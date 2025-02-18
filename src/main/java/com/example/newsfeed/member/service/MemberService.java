package com.example.newsfeed.member.service;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.findmemberdto.*;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto createMember(
            String name,
            String nickName,
            String email,
            String password,
            String passwordCheck
    ) {

        /*password와 passwordCheck가 일치하지 않는 경우*/
        if (!password.equals(passwordCheck)) {
            throw new RuntimeException("비밀번호가 서로 일치하지 않습니다.");
        }

        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(email);

        if (memberByEmail.isPresent()) {
            Member findMember = memberByEmail.get();

            if(findMember.isDeleted()) {
                throw new RuntimeException("탈퇴한 유저입니다.");
            }

            throw new RuntimeException("이미 존재하는 회원입니다.");
        }

        /*member 객체 생성 및 비밀번호 암호화*/
        Member member = new Member(name, nickName, email, passwordEncoder.encode(password));
        Member savedMember = memberRepository.save(member);

        log.info("유저 회원가입 성공");

        return new MemberResponseDto(
                savedMember.getId(),
                savedMember.getUsername(),
                savedMember.getNickname(),
                savedMember.getEmail(),
                savedMember.getCreatedAt()
        );
    }

    @Transactional
    public UpdateMemberProfileResponseDto profileUpdate(SessionMemberDto session, UpdateMemberProfileRequestDto requestDto) {
        Member member = memberRepository.findMemberById(session.getId()).orElseThrow(
                () -> new RuntimeException("id와 일치하는 유저가 없습니다.")
        );

        // info, mbti 따로 업데이트 할 수 있게 만들어주는 조건문
        // info, mbti 요청값이 없으면 이전에 있던 info,mbti 값 그대로 출력
        if(requestDto.getInfo() != null){
            member.updateInfo(requestDto.getInfo());
        }
        if(requestDto.getMbti() != null){
            member.updateMbti(requestDto.getMbti());
        }
        // 프로필 수정 저장하는 쿼리
        Member savedMember = memberRepository.save(member);

        // 프로필 수정
        member.profileUpdate(
                requestDto.getUsername(),
                requestDto.getNickname(),
                savedMember.getInfo(),
                savedMember.getMbti()
        );

        log.info("프로필 수정 성공");

        return new UpdateMemberProfileResponseDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getInfo(),
                member.getMbti(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    @Transactional
    public void updatePassword(SessionMemberDto sessionMemberDto, updatePasswordRequestDto dto) {

        Member findMember = findActiveMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

        /*본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우*/
        if(!passwordEncoder.matches(dto.getOldPassword(), findMember.getPassword())) {
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
        findMember.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        log.info("비밀번호 수정 성공");
    }

    @Transactional
    public void deleteMember(SessionMemberDto sessionMemberDto, String password) {
        Member findMember = findActiveMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new RuntimeException("입력받은 비밀번호와 유저의 비밀번호가 다름");
        }

        // hard delete 대신 isDelete 를 true 하는 방식으로 soft delete
        // 삭제된 유저 조회시 "삭제된 유저입니다" 라고 출력될 수 있도록 nickname 필드를 더티체킹
        findMember.updateIsDeleteTrueAndNickname();
    }

    private Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findMemberByEmail(email).orElseThrow(() ->
                new RuntimeException("찾아지는 이메일 유저가 없음")
        );
    }

    private Member findActiveMemberByEmailOrElseThrow(String email) {
        return memberRepository.findActiveMemberByEmail(email).orElseThrow(() ->
                new RuntimeException("탈퇴하지 않은 유저들 중에 찾아지는 이메일 유저가 없음"));
    }

    public Member findMemberByIdOrElseThrow(Long memberId) {
        return memberRepository.findMemberById(memberId).orElseThrow(() ->
                new RuntimeException("찾아지는 아이디 유저가 없음")
        );
    }

    public Member findActiveMemberByIdOrElseThrow(Long id) {
        return memberRepository.findActiveMemberById(id).orElseThrow(() ->
                new RuntimeException("탈퇴하지 않은 유저들 중에 찾아지는 id 유저가 없음"));
    }

    public Member loginMember(String email, String password) {

        Member findMember = findActiveMemberByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new RuntimeException("비밀번호가 불일치");
        }

        return findMember;
    }

    /*
    feat/member-read
    멤버 id로 멤버(최종 사용자) 프로필 조회
     */
    public FindMemberDto findMemberById(Long memberId){

        Optional<Member> optionalMember = memberRepository.findActiveMemberById(memberId);


        /* 멤버 조회에 실패한 경우
         memberRepository.findActiveMemberById(memberId);에서
         MemberRepository.java는 interface라 해당 구역에서 예외처리를 할 수가 없다 (정말사실인지 따져봐야됨)
         따라서 현 위치인 if문에서 예외처리를 해준다
        */
        if(optionalMember.isEmpty()){
            /*
             DB에서 조회하고자 하는 memberId 튜플의 isDelete 애트리뷰트가 true인 경우 MemberRepository.java의
             findActiveMemberByEmail 메서드는 null 값을 optionalMember에 return 할것이다.
             그 다음 인스턴스인 optaionalMember를 여기 if문에서 isEmpty()메서드로 optionalMember == null 인지 체크 한다
             */
            // status 404 Not Found 예외
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, memberId + "의 memberId값을 갖는 유저는 탈퇴했거나 존재하지 않습니다.");
        }

        /* 멤버 조회에 성공한 경우
        Member 인스턴스를 생성후 new연산자로 생성된 FindMemberDto에 @Getter를 이용하여
        데이터를 저장하고 caller인 MemberController에 FindMemberDto의 reference를 return한다
        */
        else{

            Member findMember = optionalMember.get();
            return new FindMemberDto(findMember.getId(), findMember.getNickname(), findMember.getEmail(), findMember.getInfo(), findMember.getMbti(), findMember.getCreatedAt());
        }

    }




    /*
    feat/member-read
    본인 유저 프로필 조회 메서드

    만드는중입니당 underconstruction!!
     */
    public FindMyMemberDto findMyMember(){

        // 로그인 세션에서 본인 memberId받아서 밑에 넣는 코드 만들꺼임
        Optional<Member> optionalMember = memberRepository.findActiveMemberById(memberId);

        /*
        본인 유저프로필을 조회하는대 다른사람 memberId를 조회할리도 없고
        예외처리 만들어봤자 시간낭비라서 안만듦ㅋ
         */

        Member findMyMember = optionalMember.get(); // 내 유저 프로필이니깐 isPresent()로 검사 안해도 됨
        return new FindMyMemberDto(findMyMember.getId(), findMyMember.getNickname(), findMyMember.getEmail(), findMyMember.getInfo(), findMyMember.getMbti(), findMyMember.getCreatedAt());

    }



}
