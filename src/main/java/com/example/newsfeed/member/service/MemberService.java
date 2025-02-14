package com.example.newsfeed.member.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member loginMember(String email, String password) {
        Member findMember = findMemberByEmailOrElseThrow(email);

        if (findMember.getPassword().equals(password)){
            throw new RuntimeException("로그인 - 가입된 유저의 비밀번호와 입력된 비밀번호의 불일치");
        }

        return findMember;
    }

    private Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findMemberByEmail(email).orElseThrow(() ->
                new RuntimeException("찾을 수 없는 이메일의 Member")
        );
    }
}
