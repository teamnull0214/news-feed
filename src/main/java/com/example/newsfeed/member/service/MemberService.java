package com.example.newsfeed.member.service;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.request.MemberRequestDto;
import com.example.newsfeed.member.dto.response.MemberResponseDto;
import com.example.newsfeed.member.dto.response.MemberUpdateProfileResponseDto;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdateProfileRequestDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
    public void updatePassword(SessionMemberDto sessionMemberDto, updatePasswordRequestDto dto) {

        Member findMember = findActiveMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

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
    public void deleteMember(SessionMemberDto sessionMemberDto, String password) {
        Member findMember = findActiveMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

        if (!PasswordEncoder.matches(password, findMember.getPassword())) {
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

        if (!PasswordEncoder.matches(password, findMember.getPassword())) {
            throw new RuntimeException("비밀번호가 불일치");
        }

        return findMember;
    }
}
