package com.example.newsfeed.member.controller;

import com.example.newsfeed.member.dto.MemberRequestDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileRequestDto;
import com.example.newsfeed.member.dto.updatedto.UpdateMemberProfileResponseDto;
import com.example.newsfeed.member.service.MemberService;
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
        return new ResponseEntity<>(memberService.createMember(
                dto.getName(),
                dto.getNickname(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPasswordCheck()
        ), HttpStatus.CREATED);
    }

    // (본인)유저 프로필 수정
    @PatchMapping("/{memberId}/profile")
    public ResponseEntity<UpdateMemberProfileResponseDto> profileUpdate(
            @PathVariable Long membersId,
            @Valid @RequestBody UpdateMemberProfileRequestDto requestDto
    ){
        log.info("유저 프로필 수정 API 호출");
        return ResponseEntity.ok(memberService.profileUpdate(membersId,requestDto));
    }
}
