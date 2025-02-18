package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
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
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @LoginRequired
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createFollow(
            @PathVariable Long memberId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        followService.createFollow(session.getId(), memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
