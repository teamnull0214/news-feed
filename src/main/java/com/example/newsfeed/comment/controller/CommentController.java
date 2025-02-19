package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.createdto.CommentCreateRequestDto;
import com.example.newsfeed.comment.dto.createdto.CommentCreateResponseDto;
import com.example.newsfeed.comment.dto.updatedto.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.updatedto.CommentUpdateResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.ApiResponseDtoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    // 추후에 Validation 사용시 각기능에 @Valid 추가

    // 댓글 생성
    @LoginRequired
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<CommentCreateResponseDto>> createComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto requestDto
    ){

        ApiResponseDtoImpl<CommentCreateResponseDto> reponse = new ApiResponseDtoImpl<>();
        reponse.ok(commentService.createComment(session, postId, requestDto));

        return ResponseEntity.ok(reponse);
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<List<CommentResponseDto>>> findByComment(@PathVariable Long postId){

        ApiResponseDtoImpl<List<CommentResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.findByComment(postId));

        return ResponseEntity.ok(response);
    }


    // 댓글수정
    @LoginRequired
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto requestDto
    ){
        return ResponseEntity.ok(commentService.updateComment(session, postId, commentId, requestDto));
    }

    // 해당 댓글 삭제
    @LoginRequired
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(session, postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
