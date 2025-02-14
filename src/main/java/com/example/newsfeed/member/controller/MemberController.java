package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    /*
    members/{memberId}/delete 에서 members/delete 로 수정
    세션 값으로 memberId 가져옴
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMember(
            @RequestBody Map<String, String> dto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        SessionMemberDto sessionMemberDto = (SessionMemberDto) session.getAttribute("member");
        if (sessionMemberDto == null) {
            throw new RuntimeException("비로그인 사용자 접근 - 로그인 필요");
        }
        memberService.deleteMember(sessionMemberDto, dto.get("password"));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
