package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.service.FollowListService;
import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.ApiResponseDtoImpl;
import com.example.newsfeed.member.dto.response.MemberListGetResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowListService followListService;
    private final FollowService followService;

    @LoginRequired
    @PostMapping("/{memberId}")
    public ResponseEntity<ApiResponseDto<Void>> createFollow(
            @PathVariable Long memberId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        followService.createFollow(session.getId(), memberId);

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("팔로우 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteFollow(
            @PathVariable Long memberId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        followService.deleteFollow(session.getId(), memberId);

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("팔로우 취소 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<MemberListGetResponseDto>>> findAllFollowerMembers(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponseDtoImpl<Page<MemberListGetResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(followListService.findAllFollowerMembers(session.getId(), page, size));

        log.info("팔로우 목록 전체 조회");
        return ResponseEntity.ok(response);
    }
}
