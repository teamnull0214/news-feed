package com.example.newsfeed.member.service;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
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
    public MemberResponseDto createMember(
            String name,
            String nickName,
            String email,
            String password,
            String passwordCheck
    ) {
        /*password와 passwordCheck가 일치하지 않는 경우*/
        if (!password.equals(passwordCheck) ) {
            throw new RuntimeException("비밀번호가 서로 일치하지 않습니다.");
        }

        /*이미 존재하는 회원인 경우*/
        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(email);
        if (memberByEmail.isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }

        /*member 객체 생성*/
        Member member = new Member(name, nickName, email, password);
        Member savedMember = memberRepository.save(member);

        /*todo: 비밀번호 암호화 추가하기*/

        log.info("유저 회원가입 성공");

        return new MemberResponseDto(
                savedMember.getId(),
                savedMember.getUsername(),
                savedMember.getNickname(),
                savedMember.getEmail(),
                savedMember.getCreatedAt()
        );
    }

    public UpdateMemberProfileResponseDto profileUpdate(Long membersId, UpdateMemberProfileRequestDto requestDto) {

        Member member = memberRepository.findMemberById(membersId).orElseThrow(
                () -> new RuntimeException("id와 일치하는 유저가 없습니다.")
        );

        // 프로필 수정
        member.profileUpdate(
                requestDto.getName(),
                requestDto.getNickname(),
                requestDto.getInfo(),
                requestDto.getMbti()
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

        Member findMember = findMemberByIdOrElseThrow(sessionMemberDto.getId());

        /*본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우*/
        if(!findMember.getPassword().equals(dto.getOldPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        /*현재 비밀번호와 동일한 비밀번호로 수정하는 경우*/
        if(findMember.getPassword().equals(dto.getNewPassword())) {
            throw new RuntimeException("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        /*새 비밀번호와 새 비밀번호 확인이 일치하지 않은 경우*/
        if(!dto.getNewPassword().equals(dto.getNewPasswordCheck())) {
            throw new RuntimeException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        /*비밀번호 업데이트*/
        findMember.updatePassword(dto.getNewPassword());

        log.info("비밀번호 수정 성공");
    }

    @Transactional
    public void deleteMember(SessionMemberDto sessionMemberDto, String password) {
        Member findMember = findMemberByEmailOrElseThrow(sessionMemberDto.getEmail());

        if (findMember.getPassword().equals(password)) {
            throw new RuntimeException("입력받은 비밀번호와 유저의 비밀번호가 다름");
        }

        findMember.updateIsDelete(true);
    }

    private Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findMemberByEmail(email).orElseThrow(() ->
                new RuntimeException("찾아지는 이메일 유저가 없음")
        );
    }

    private Member findMemberByIdOrElseThrow(Long memebrId) {
        return memberRepository.findMemberById(memebrId).orElseThrow(() ->
                new RuntimeException("찾아지는 아이디 유저가 없음")
        );
    }


    public Member loginMember(String email, String password) {
        Member findMember = findMemberByEmailOrElseThrow(email);
        if (!findMember.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 불일치");
        }

        return findMember;
    }
}
