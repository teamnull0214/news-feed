package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.LoginRequestDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> loginMember(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession == null) {
            httpSession = httpServletRequest.getSession();
        }

        Member findLoginMember = memberService.loginMember(dto.getEmail(), dto.getPassword());
        if (findLoginMember != null) {
            httpSession.setAttribute("member", new SessionMemberDto(
                    findLoginMember.getId(),
                    findLoginMember.getUsername(),
                    findLoginMember.getNickname(),
                    findLoginMember.getEmail()
            ));
            httpSession.setMaxInactiveInterval(1800);       // 세션 만료시간 30분

            log.info("로그인 성공 유저 = {}, {}, {},{}",
                    findLoginMember.getId(),
                    findLoginMember.getUsername(),
                    findLoginMember.getNickname(),
                    findLoginMember.getEmail()
            );

            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new RuntimeException("로그인 실패 - 사용자를 찾지 못했을 경우");
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutMember(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
