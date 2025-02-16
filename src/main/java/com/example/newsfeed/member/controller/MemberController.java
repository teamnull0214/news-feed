package com.example.newsfeed.member.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.dto.MemberRequestDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.updatePasswordRequestDto;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        return new ResponseEntity<>(memberService.createMember(
                dto.getName(),
                dto.getNickname(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPasswordCheck()
        ), HttpStatus.CREATED);
    }

    /*유저의 비밀번호 업데이트*/
    @PatchMapping("/members/{memberId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long memberId,
            @Valid @RequestBody updatePasswordRequestDto dto
    ) {
        /* todo: 로그인 기능 merge 후 세션 추가*/

        memberService.updatePassword(
                memberId,
                dto.getOldPassword(),
                dto.getNewPassword(),
                dto.getNewPasswordCheck()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMember(
            @RequestBody Map<String, String> dto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        SessionMemberDto sessionMemberDto = (SessionMemberDto) session.getAttribute("member");
        memberService.deleteMember(sessionMemberDto, dto.get("password"));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
