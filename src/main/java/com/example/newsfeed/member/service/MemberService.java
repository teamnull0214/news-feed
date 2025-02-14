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


}
