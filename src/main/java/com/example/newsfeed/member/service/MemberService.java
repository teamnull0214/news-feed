package com.example.newsfeed.member.service;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.request.MemberRequestDto;
import com.example.newsfeed.member.dto.response.MemberResponseDto;
import com.example.newsfeed.member.dto.response.MemberUpdateProfileResponseDto;
import com.example.newsfeed.member.dto.request.MemberUpdatePasswordRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdateProfileRequestDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.findmemberdto.*;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member loginMember(String email, String password) {

        Member findMember = findActiveMemberByEmailOrElseThrow(email);

        if (!PasswordEncoder.matches(password, findMember.getPassword())) {
            throw new RuntimeException("비밀번호가 불일치");
        }
        return findMember;
    }

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto dto) {

        /*password와 passwordCheck가 일치하지 않는 경우*/
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new RuntimeException("비밀번호가 서로 일치하지 않습니다.");
        }

        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(dto.getEmail());
        if (memberByEmail.isPresent()) {
            Member findMember = memberByEmail.get();

            if(findMember.isDeleted()) {
                throw new RuntimeException("탈퇴한 유저입니다.");
            }
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }

        /*member 객체 생성 및 비밀번호 암호화*/
        Member member = dto.toEntity();
        memberRepository.save(member);

        log.info("유저 회원가입 성공");
        return MemberResponseDto.toDto(member);
    }

    @Transactional
    public MemberUpdateProfileResponseDto profileUpdate(SessionMemberDto session, MemberUpdateProfileRequestDto dto) {

        Member member = findActiveMemberByIdOrElseThrow(session.getId());

        String updatedUsername = (dto.getUsername() != null) ? dto.getUsername() : member.getUsername();
        String updatedNickname = (dto.getNickname() != null) ? dto.getNickname() : member.getNickname();
        member.updateUsernameAndNickname(updatedUsername, updatedNickname);

        // info, mbti 따로 업데이트 할 수 있게 만들어주는 조건문
        // info, mbti 요청값이 없으면 이전에 있던 info,mbti 값 그대로 출력
        if(dto.getInfo() != null){
            member.updateInfo(dto.getInfo());
        }
        if(dto.getMbti() != null){
            member.updateMbti(dto.getMbti());
        }

        log.info("프로필 수정 성공");
        return MemberUpdateProfileResponseDto.toDto(member);
    }

    @Transactional
    public void updatePassword(SessionMemberDto session, MemberUpdatePasswordRequestDto dto) {

        Member findMember = findActiveMemberByEmailOrElseThrow(session.getEmail());

        /*본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우*/
        if(!PasswordEncoder.matches(dto.getOldPassword(), findMember.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        /*현재 비밀번호와 동일한 비밀번호로 수정하는 경우*/
        if(dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new RuntimeException("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        /*새 비밀번호와 새 비밀번호 확인이 일치하지 않은 경우*/
        if(!dto.getNewPassword().equals(dto.getNewPasswordCheck())) {
            throw new RuntimeException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        /*비밀번호 암호화 후 업데이트*/
        findMember.updatePassword(PasswordEncoder.encode(dto.getNewPassword()));
        log.info("비밀번호 수정 성공");
    }

    @Transactional
    public void deleteMember(SessionMemberDto session, String password) {
        Member findMember = findActiveMemberByEmailOrElseThrow(session.getEmail());

        if (!PasswordEncoder.matches(password, findMember.getPassword())) {
            throw new RuntimeException("입력받은 비밀번호와 유저의 비밀번호가 다름");
        }
        findMember.updateIsDeleteTrueAndNickname();
    }

    private Member findActiveMemberByEmailOrElseThrow(String email) {
        return memberRepository.findActiveMemberByEmail(email).orElseThrow(() ->
                new RuntimeException("탈퇴하지 않은 유저들 중에 찾아지는 이메일 유저가 없음"));
    }

    public Member findActiveMemberByIdOrElseThrow(Long id) {
        return memberRepository.findActiveMemberById(id).orElseThrow(() ->
                new RuntimeException("탈퇴하지 않은 유저들 중에 찾아지는 id 유저가 없음"));
    }

    /*
    feat/member-read
    멤버 id로 멤버(최종 사용자) 프로필 조회
     */
    public FindMemberDto findMemberById(Long memberId){

        // 람다식으로 깔끔하게 예외처리와 sql조회를 동시에~~ made by queenriwon
        Member member = memberRepository.findActiveMemberById(memberId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, memberId + "의 memberId값을 갖는 유저는 탈퇴했거나 존재하지 않습니다."));

        // 지우긴 싫어서 주석으로 남기는대 지우고 싶으면 지우십쇼zzzz 아래 코드들이 람다식으로 다 쓸모가 없어져 부렸으
        //Optional<Member> optionalMember = memberRepository.findActiveMemberById(memberId);

        /* 멤버 조회에 실패한 경우
         memberRepository.findActiveMemberById(memberId);에서
         MemberRepository.java는 interface라 해당 구역에서 예외처리를 할 수가 없다 (정말사실인지 따져봐야됨)
         따라서 현 위치인 if문에서 예외처리를 해준다
        */

        //if(optionalMember.isEmpty()){
            /*
             DB에서 조회하고자 하는 memberId 튜플의 isDelete 애트리뷰트가 true인 경우 MemberRepository.java의
             findActiveMemberByEmail 메서드는 null 값을 optionalMember에 return 할것이다.
             그 다음 인스턴스인 optaionalMember를 여기 if문에서 isEmpty()메서드로 optionalMember == null 인지 체크 한다
             */
            // status 404 Not Found 예외
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, memberId + "의 memberId값을 갖는 유저는 탈퇴했거나 존재하지 않습니다.");
        //}


        /* 멤버 조회에 성공한 경우 (일반적인 상황)
        Member 인스턴스를 생성후 new연산자로 생성된 FindMemberDto에 @Getter를 이용하여
        데이터를 저장하고 caller인 MemberController에 FindMemberDto의 reference를 return한다
        */
        //Member findMember = member;
        return new FindMemberDto(member.getId(), member.getNickname(), member.getEmail(), member.getInfo(), member.getMbti(), member.getCreatedAt());

    }


     /*
       feat/member-read
       본인 유저 프로필 조회 메서드
       MemberController.java -> findMyMember 메서드 상단에 @LoginRequired custom annotaion으로 로그인 세션이
       null이면(즉 로그인을 안한 비회원 상태라면) 해당 기능에 접근을 막아 해당 코드에서 예외처리를 따로 안해도 된다.
    */
    public FindMyMemberDto findMyMember(SessionMemberDto currSession){

        // MemberController에서 전달받은 현재 로그인 세션에서 getId()를 통해 DB에서 유저프로필 조회
        // Member member = memberRepository.findActiveMemberById(memberId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, memberId + "의 memberId값을 갖는 유저는 탈퇴했거나 존재하지 않습니다."));
        // 위의 코드를 사용해서 예외처리와 조회를 동시에 할 수도있다
        Optional<Member> optionalMember = memberRepository.findActiveMemberById(currSession.getId());

        /*
        MemberController.java -> findMyMember 메서드 상단에 @LoginRequired custom annotaion으로 로그인 세션이
        null이면(즉 로그인을 안한 비회원 상태라면) 메서드 실행을 막아 현재 이 코드에서 예외처리를 따로 안해도 된다.
         */

        Member findMyMember = optionalMember.get(); // 내 유저 프로필이니깐 isPresent()로 검사 안해도 상관없다
        return new FindMyMemberDto(findMyMember.getId(),findMyMember.getUsername(), findMyMember.getNickname(), findMyMember.getEmail(), findMyMember.getInfo(), findMyMember.getMbti(), findMyMember.getCreatedAt(), findMyMember.getModifiedAt());
    }


}
