package com.example.newsfeed.member.service;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.global.exception.custom.*;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static com.example.newsfeed.global.exception.ErrorCode.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /*todo: 각 서비스단 성공 응답 만들기*/

    @Transactional
    public MemberResponseDto createMember(
            String name,
            String nickName,
            String email,
            String password,
            String passwordCheck
    ) {

        /*password와 passwordCheck가 일치하지 않는 경우*/
        if (!password.equals(passwordCheck) ) {
            throw new ForbiddenException.PasswordConfirmMismatchException(PASSWORD_CONFIRM_MISMATCH);
        }

        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(email);

        if (memberByEmail.isPresent()) {
            Member findMember = memberByEmail.get();

            if(findMember.isDeleted()) {
                throw new ForbiddenException.MemberDeactivedException(MEMBER_DEACTIVATED);
            }

            throw new ConflictException.MemberAlreadyExistsException(MEMBER_ALREADY_EXISTS);
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
                () -> new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND)
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
            throw new ForbiddenException.PasswordMismatchException(PASSWORD_MISMATCH);
        }

        /*현재 비밀번호와 동일한 비밀번호로 수정하는 경우*/
        if(dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new ForbiddenException.NewPasswordSameAsOldException(NEW_PASSWORD_SAME_AS_OLD);
        }

        /*새 비밀번호와 새 비밀번호 확인이 일치하지 않은 경우*/
        if(!dto.getNewPassword().equals(dto.getNewPasswordCheck())) {
            throw new ForbiddenException.PasswordConfirmMismatchException(PASSWORD_CONFIRM_MISMATCH);
        }

        /*비밀번호 암호화 후 업데이트*/
        findMember.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        log.info("비밀번호 수정 성공");
    }

    @Transactional
    public void deleteMember(SessionMemberDto sessionMemberDto, String password) {
        Member findMember = findActiveMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new ForbiddenException.PasswordMismatchException(PASSWORD_MISMATCH);
        }

        // hard delete 대신 isDelete 를 true 하는 방식으로 soft delete
        // 삭제된 유저 조회시 "삭제된 유저입니다" 라고 출력될 수 있도록 nickname 필드를 더티체킹
        findMember.updateIsDeleteTrueAndNickname();
    }

    private Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findMemberByEmail(email).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND)
        );
    }

    private Member findActiveMemberByEmailOrElseThrow(String email) {
        return memberRepository.findActiveMemberByEmail(email).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    public Member findMemberByIdOrElseThrow(Long memberId) {
        return memberRepository.findMemberById(memberId).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND)
        );
    }

    public Member findActiveMemberByIdOrElseThrow(Long id) {
        return memberRepository.findActiveMemberById(id).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    public Member loginMember(String email, String password) {

        Member findMember = findActiveMemberByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new ForbiddenException.PasswordMismatchException(PASSWORD_MISMATCH);
        }

        return findMember;
    }
}
