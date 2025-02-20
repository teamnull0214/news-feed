package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.custom.NotFoundException;
import com.example.newsfeed.member.dto.request.LoginRequestDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;
import static com.example.newsfeed.global.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<?>> loginMember(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession == null) {
            httpSession = httpServletRequest.getSession();
        }

        Member findLoginMember = memberService.loginMember(dto.getEmail(), dto.getPassword());
        if (findLoginMember != null) {
            httpSession.setAttribute(LOGIN_MEMBER, new SessionMemberDto(
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

            return ResponseEntity.ok(ok(null));
        }
        throw new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND);
    }

    @LoginRequired
    @GetMapping("/logout")
    public ResponseEntity<ApiResponseDto<?>> logoutMember(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        log.info("로그아웃 성공");
        return ResponseEntity.ok(ok(null));
    }
}
