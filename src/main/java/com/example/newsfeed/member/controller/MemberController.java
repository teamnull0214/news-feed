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

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*회원가입 기능 구현*/
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseDto<MemberResponseDto>> createMember(
            @Valid @RequestBody MemberRequestDto dto
    ) {

        ApiResponseDtoImpl<MemberResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(memberService.createMember(dto));

        log.info("회원가입 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<MemberMyGetResponseDto>> findMyMember(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto currSession
    ) {
        ApiResponseDtoImpl<MemberMyGetResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(memberService.findMyMember(currSession));

        log.info("본인 프로필 조회 성공");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponseDto<MemberGetResponseDto>> findMemberById(@PathVariable Long memberId)
    {
        ApiResponseDtoImpl<MemberGetResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(memberService.findMemberById(memberId));

        log.info("다른 유저 프로필 조회 성공");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<MemberListGetResponseDto>>> findAllMemberPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponseDtoImpl<Page<MemberListGetResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(memberService.findAllMemberPage(page, size));

        log.info("유저 목록 전체 조회 성공");
        return ResponseEntity.ok(response);
    }

    // (본인)유저 프로필 수정
    @LoginRequired
    @PatchMapping("/profile")
    public ResponseEntity<ApiResponseDto<MemberMyGetResponseDto>> updateMemberProfile(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @Valid @RequestBody MemberUpdateProfileRequestDto dto,
            HttpServletRequest httpServletRequest
    ){
        if (dto.getNickname() != null) {
            HttpSession httpSession = httpServletRequest.getSession(false);
            httpSession.setAttribute(LOGIN_MEMBER, session.setNickname(dto.getNickname()));
        }
        ApiResponseDtoImpl<MemberMyGetResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(memberService.updateMemberProfile(session, dto));

        log.info("본인 프로필 수정 성공");
        return ResponseEntity.ok(response);
    }

    /*유저의 비밀번호 업데이트*/
    @LoginRequired
    @PatchMapping("/password")
    public ResponseEntity<ApiResponseDto<Void>> updateMemberPassword(
            @Valid @RequestBody MemberUpdatePasswordRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        memberService.updateMemberPassword(session, dto);

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("비밀번호 수정 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @PostMapping("/delete")
    public ResponseEntity<ApiResponseDto<Void>> deleteMember(
            @Valid @RequestBody MemberDeleteRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        memberService.deleteMember(session, dto.getPassword());

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("유저 탈퇴 성공");
        return ResponseEntity.ok(response);
    }
}
