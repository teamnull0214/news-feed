package com.example.newsfeed.post.controller;

import com.example.newsfeed.global.exception.ApiResponseDto;
import com.example.newsfeed.global.exception.ApiResponseDtoImpl;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성 (로그인 상태)
    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponseDto<PostResponseDto>> createPost(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @Valid @RequestBody PostRequestDto requestDto
    ) {
        ApiResponseDtoImpl<PostResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(postService.createPost(session, requestDto));

        log.info("게시물 생성 성공");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<PostResponseDto>>> findAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponseDtoImpl<Page<PostResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(postService.findAllPost(page, size));

        log.info("게시물 전체 조회 성공");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<PostResponseDto>> findPostById(@PathVariable Long postId) {

        ApiResponseDtoImpl<PostResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(postService.findPostById(postId));

        log.info("게시물 단건 조회 성공");
        return ResponseEntity.ok(response);

    }

    /*게시물 전체 조회*/
    @GetMapping("/sorted-by-created")
    public ResponseEntity<ApiResponseDto<Page<PostResponseDto>>> findPostsSortedByModified(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponseDtoImpl<Page<PostResponseDto>> response = new ApiResponseDtoImpl<>();
        response.ok(postService.findPostsSortedByModifiedAt(startDate, endDate, page, size));

        log.info("기간별 게시물 전체 조회 성공");
        return ResponseEntity.ok(response);
    }

    // 포스트 업데이트
    @LoginRequired
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<PostResponseDto>> updatePostImageAndContents(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {

        ApiResponseDtoImpl<PostResponseDto> response = new ApiResponseDtoImpl<>();
        response.ok(postService.updatePostImageAndContents(postId, session.getId(), dto));

        log.info("게시물 수정 성공");
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제
    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<Void>> deletePost(
            @PathVariable Long postId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        postService.deletePost(postId, session.getId());

        ApiResponseDtoImpl<Void> response = new ApiResponseDtoImpl<>();
        response.ok(null);

        log.info("게시물 삭제 성공");
        return ResponseEntity.ok(response);
    }
}