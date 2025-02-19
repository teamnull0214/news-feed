package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentRequestDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.ApiResponseDtoImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*댓글 생성*/
    @LoginRequired
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> createComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {

        ApiResponseDtoImpl<CommentResponseDto> reponse = new ApiResponseDtoImpl<>();
        reponse.ok(commentService.createComment(session, postId, requestDto));

        log.info("댓글 생성 성공");
        return ResponseEntity.ok(reponse);
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponseDto<Page<CommentResponseDto>>> findCommentAllByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponseDtoImpl<Page<CommentResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.findCommentsOnPost(postId, page, size));

        log.info("댓글 전체 조회 성공");
        return ResponseEntity.ok(response);
    }

    // 댓글수정
    @LoginRequired
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> updateComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        ApiResponseDtoImpl<CommentResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(commentService.updateComment(session, commentId, requestDto));

        log.info("댓글 수정 성공");
        return ResponseEntity.ok(response);
    }

    // 해당 댓글 삭제
    @LoginRequired
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteComment(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(session, commentId);

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("댓글 삭제 성공");
        return ResponseEntity.ok(response);
    }
}
