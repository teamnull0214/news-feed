package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.ApiResponseDtoImpl;
import com.example.newsfeed.member.dto.request.MemberDeleteRequestDto;
import com.example.newsfeed.member.dto.request.MemberRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdatePasswordRequestDto;
import com.example.newsfeed.member.dto.request.MemberUpdateProfileRequestDto;
import com.example.newsfeed.member.dto.response.MemberGetResponseDto;
import com.example.newsfeed.member.dto.response.MemberListGetResponseDto;
import com.example.newsfeed.member.dto.response.MemberResponseDto;
import com.example.newsfeed.member.dto.response.MemberMyGetResponseDto;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*회원가입 기능 구현*/
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseDto<?>> createMember(
            @Valid @RequestBody MemberRequestDto dto
    ) {

        log.info("회원가입 성공");
        return ResponseEntity.ok(ok(memberService.createMember(dto)));
    }

    @LoginRequired
    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<?>> findMyMember(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto currSession
    ) {

        log.info("본인 프로필 조회 성공");
        return ResponseEntity.ok(ok(memberService.findMyMember(currSession)));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponseDto<?>> findMemberById(@PathVariable Long memberId)
    {

        log.info("다른 유저 프로필 조회 성공");
        return ResponseEntity.ok(ok(memberService.findMemberById(memberId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<?>> findAllMemberPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        log.info("유저 목록 전체 조회 성공");
        return ResponseEntity.ok(ok(memberService.findAllMemberPage(page, size)));
    }

    // (본인)유저 프로필 수정
    @LoginRequired
    @PatchMapping("/profile")
    public ResponseEntity<ApiResponseDto<?>> updateMemberProfile(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @Valid @RequestBody MemberUpdateProfileRequestDto dto,
            HttpServletRequest httpServletRequest
    ){
        if (dto.getNickname() != null) {
            HttpSession httpSession = httpServletRequest.getSession(false);
            httpSession.setAttribute(LOGIN_MEMBER, session.setNickname(dto.getNickname()));
        }

        log.info("본인 프로필 수정 성공");
        return ResponseEntity.ok(ok(memberService.updateMemberProfile(session, dto)));
    }

    /*유저의 비밀번호 업데이트*/
    @LoginRequired
    @PatchMapping("/password")
    public ResponseEntity<ApiResponseDto<?>> updateMemberPassword(
            @Valid @RequestBody MemberUpdatePasswordRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        memberService.updateMemberPassword(session, dto);

        log.info("비밀번호 수정 성공");
        return ResponseEntity.ok(ok(null));
    }

    @LoginRequired
    @PostMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteMember(
            @Valid @RequestBody MemberDeleteRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        memberService.deleteMember(session, dto.getPassword());

        log.info("유저 탈퇴 성공");
        return ResponseEntity.ok(ok(null));
    }
}
