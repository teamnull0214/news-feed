package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.request.MemberRequestDto;
import com.example.newsfeed.member.dto.response.MemberResponseDto;
import com.example.newsfeed.member.dto.request.MemberDeleteRequestDto;
import com.example.newsfeed.member.dto.response.MemberUpdateProfileResponseDto;
import com.example.newsfeed.member.dto.request.MemberUpdatePasswordRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdateProfileRequestDto;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*회원가입 기능 구현*/
    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> createMember(
            @Valid @RequestBody MemberRequestDto dto
    ) {
        log.info("회원가입 API 호출");
        return new ResponseEntity<>(memberService.createMember(dto), HttpStatus.CREATED);
    }

    // (본인)유저 프로필 수정
    @LoginRequired
    @PatchMapping("/profile")
    public ResponseEntity<MemberUpdateProfileResponseDto> profileUpdate(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @Valid @RequestBody MemberUpdateProfileRequestDto dto,
            HttpServletRequest httpServletRequest
    ){
        if (dto.getNickname() != null) {
            HttpSession httpSession = httpServletRequest.getSession(false);
            httpSession.setAttribute("member", session.setNickname(dto.getNickname()));
        }
        log.info("유저 프로필 수정");
        return ResponseEntity.ok(memberService.profileUpdate(session, dto));
    }

    /*유저의 비밀번호 업데이트*/
    @LoginRequired
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody MemberUpdatePasswordRequestDto dto,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        memberService.updatePassword(session, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMember(
            @Valid @RequestBody MemberDeleteRequestDto dto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        SessionMemberDto sessionMemberDto = (SessionMemberDto) session.getAttribute("member");
        memberService.deleteMember(sessionMemberDto, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
