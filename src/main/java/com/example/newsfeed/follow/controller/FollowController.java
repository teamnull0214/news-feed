package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.member.dto.response.MemberListGetResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.newsfeed.global.constant.SessionConst.LOGIN_MEMBER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @LoginRequired
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createFollow(
            @PathVariable Long memberId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        followService.createFollow(session.getId(), memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable Long memberId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        followService.deleteFollow(session.getId(), memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @GetMapping
    public ResponseEntity<List<MemberListGetResponseDto>> findAllFollowerMembers(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        List<MemberListGetResponseDto> responseDtoList = followService.findAllFollowerMembers(session.getId());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
}
