package com.example.newsfeed.member.service;

import com.example.newsfeed.follow.service.FollowListService;
import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.global.exception.custom.*;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.findmemberdto.*;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileResponseDto;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.member.dto.request.MemberRequestDto;
import com.example.newsfeed.member.dto.response.MemberGetResponseDto;
import com.example.newsfeed.member.dto.response.MemberListGetResponseDto;
import com.example.newsfeed.member.dto.response.MemberResponseDto;
import com.example.newsfeed.member.dto.response.MemberMyGetResponseDto;
import com.example.newsfeed.member.dto.request.MemberUpdatePasswordRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdateProfileRequestDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import static com.example.newsfeed.global.exception.ErrorCode.*;


import static com.example.newsfeed.global.constant.EntityConstants.ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowListService followService;

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
                throw new ForbiddenException.MemberDeactivedException(MEMBER_DEACTIVATED);
            }
            throw new ConflictException.MemberAlreadyExistsException(MEMBER_ALREADY_EXISTS);
        }

        /*member 객체 생성 및 비밀번호 암호화*/
        Member member = dto.toEntity();
        memberRepository.save(member);

        log.info("유저 회원가입 성공");
        return MemberResponseDto.toDto(member);
    }

    /* 세션 본인 정보 조회 */
    public MemberMyGetResponseDto findMyMember(SessionMemberDto session){
        Member member = findActiveMemberByIdOrElseThrow(session.getId());
        return MemberMyGetResponseDto.toDto(member);
    }

    /* 상세한 타인 정보 조회 */
    public MemberGetResponseDto findMemberById(Long memberId){
        Member member = findActiveMemberByIdOrElseThrow(memberId);
        return MemberGetResponseDto.toDto(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberListGetResponseDto> findAllMemberPage(int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(ID));
        Page<Member> memberPage = memberRepository.findActiveMemberAll(pageable);

        List<MemberListGetResponseDto> dtoList = memberPage.getContent().stream()
                .map(member -> MemberListGetResponseDto.toDto(member, followService.countByFollowingMemberId(member.getId())))
                .toList();

        return new PageImpl<>(dtoList, pageable, memberPage.getTotalElements());
    }


    @Transactional
    public MemberMyGetResponseDto updateMemberProfile(SessionMemberDto session, MemberUpdateProfileRequestDto dto) {

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
        return MemberMyGetResponseDto.toDto(member);
    }

    @Transactional
    public void updateMemberPassword(SessionMemberDto session, MemberUpdatePasswordRequestDto dto) {

        Member findMember = findActiveMemberByEmailOrElseThrow(session.getEmail());

        /*본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우*/
        if(!PasswordEncoder.matches(dto.getOldPassword(), findMember.getPassword())) {
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
        findMember.updatePassword(PasswordEncoder.encode(dto.getNewPassword()));
        log.info("비밀번호 수정 성공");
    }

    @Transactional
    public void deleteMember(SessionMemberDto session, String password) {
        Member findMember = findActiveMemberByEmailOrElseThrow(session.getEmail());

        if (!PasswordEncoder.matches(password, findMember.getPassword())) {
            throw new ForbiddenException.PasswordMismatchException(PASSWORD_MISMATCH);
        }
        findMember.updateIsDeleteTrueAndNickname();
    }

    private Member findActiveMemberByEmailOrElseThrow(String email) {
        return memberRepository.findActiveMemberByEmail(email).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    public Member findActiveMemberByIdOrElseThrow(Long id) {
        return memberRepository.findActiveMemberById(id).orElseThrow(() ->
                new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND));
    }

}
