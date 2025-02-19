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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @LoginRequired
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<CommentCreateResponseDto>> createComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto requestDto
    ){

        ApiResponseDtoImpl<CommentCreateResponseDto> reponse = new ApiResponseDtoImpl<>();
        reponse.ok(commentService.createComment(session, postId, requestDto));

        log.info("댓글 생성 성공");
        return ResponseEntity.ok(reponse);
    }

    // 추후에 Validation 사용시 각기능에 @Valid 추가
    // 댓글 생성
    // 댓글 조회
    // 댓글수정
    // 해당 댓글 삭제
    // 댓글 페이징 구현
    @GetMapping("/posts/{postId}/page")
    public ResponseEntity<ApiResponseDto<Page<CommentResponseDto>>> findCommentsOnPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        ApiResponseDtoImpl<Page<CommentResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.findCommentsOnPost(postId, page, size));

        log.info("댓글 페이징 전체 조회 성공");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<List<CommentResponseDto>>> findByComment(@PathVariable Long postId){

        ApiResponseDtoImpl<List<CommentResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.findByComment(postId));

        log.info("댓글 전체 조회 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<CommentUpdateResponseDto>> updateComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto requestDto
    ){
        ApiResponseDtoImpl<CommentUpdateResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.updateComment(session, postId, commentId, requestDto));

        log.info("댓글 수정 성공");
        return ResponseEntity.ok(response);
    }

    @LoginRequired
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteComment(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(session, postId, commentId);

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("댓글 삭제 성공");
        return ResponseEntity.ok(response);
    }
}
