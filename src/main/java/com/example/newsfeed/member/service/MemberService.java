package com.example.newsfeed.member.service;

import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Transactional
    public void updatePassword(Long memberId, String oldPassword, String newPassword, String newPasswordCheck) {

        Optional<Member> memberById = memberRepository.findMemberById(memberId);

        /* todo: 유저가 존재하지 않는 경우 (로그인 기능 pull 받은 후 수정 예정)*/
        if (memberById.isEmpty()) {
            throw new RuntimeException("해당 유저는 존재하지 않습니다.");
        }

        Member member = memberById.get();

        /*todo: 세션을 통해 다른 유저가 수정하는 것을 막도록 한다.(추가 예정)*/

        /*본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우*/
        if(!member.getPassword().equals(oldPassword)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        /*현재 비밀번호와 동일한 비밀번호로 수정하는 경우*/
        if(!member.getPassword().equals(newPassword)) {
            throw new RuntimeException("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        /*새 비밀번호와 새 비밀번호 확인이 일치하지 않은 경우*/
        if(newPassword.equals(newPasswordCheck)) {
            throw new RuntimeException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
    }
}
