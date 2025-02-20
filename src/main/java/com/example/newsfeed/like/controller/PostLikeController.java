package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController{

    private final PostLikeService postLikeService;

    @LoginRequired
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponseDto<?>> createLike(
            @PathVariable Long postId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        postLikeService.createLike(session.getId(), postId);

        log.info("게시글에 좋아요 누르기 성공");
        return ResponseEntity.ok(ok(null));
    }

    @LoginRequired
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponseDto<?>> deleteLike(
            @PathVariable Long postId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        postLikeService.deleteLike(session.getId(), postId);

        log.info("게시글에 좋아요 취소 성공");
        return ResponseEntity.ok(ok(null));
    }
}
