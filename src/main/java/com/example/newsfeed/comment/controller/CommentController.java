package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentRequestDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.newsfeed.global.constant.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @LoginRequired
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
        return ResponseEntity.ok(commentService.createComment(session, postId, requestDto));
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentAllByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.findCommentAllByPostId(postId));
    }

    // 댓글수정
    @LoginRequired
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
        return ResponseEntity.ok(commentService.updateComment(session, commentId, requestDto));
    }

    // 해당 댓글 삭제
    @LoginRequired
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(session, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
