package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentRequestDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*댓글 생성*/
    @LoginRequired
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<?>> createComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        log.info("댓글 생성 성공");
        return ResponseEntity.ok(ok(commentService.createComment(session, postId, requestDto)));
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<?>> findCommentAllByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        log.info("댓글 전체 조회 성공");
        return ResponseEntity.ok(ok(commentService.findCommentsOnPost(postId, page, size)));
    }

    // 댓글수정
    @LoginRequired
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<?>> updateComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        log.info("댓글 수정 성공");
        return ResponseEntity.ok(ok(commentService.updateComment(session, commentId, requestDto)));
    }

    // 해당 댓글 삭제
    @LoginRequired
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<?>> deleteComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(session, commentId);

        log.info("댓글 삭제 성공");
        return ResponseEntity.ok(ok(null));
    }
}
