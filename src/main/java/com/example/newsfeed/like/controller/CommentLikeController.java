package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentLikeController{

    private final CommentLikeService commentLikeService;

    @LoginRequired
    @PostMapping("/{commentId}/likes")
    public ResponseEntity<ApiResponseDto<?>> createLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        commentLikeService.createLike(session.getId(), commentId);

        log.info("댓글 좋아요 누르기 성공");
        return ResponseEntity.ok(ok(null));
    }

    @LoginRequired
    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<ApiResponseDto<?>> deleteLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        commentLikeService.deleteLike(session.getId(), commentId);

        log.info("댓글 좋아요 취소 성공");
        return ResponseEntity.ok(ok(null));
    }
}
