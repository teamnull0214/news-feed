package com.example.newsfeed.post.controller;

import com.example.newsfeed.global.exception.ApiResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;
import static com.example.newsfeed.global.exception.ApiResponseDto.ok;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성 (로그인 상태)
    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponseDto<?>> createPost(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @Valid @RequestBody PostRequestDto requestDto
    ) {

        log.info("게시물 생성 성공");
        return ResponseEntity.ok(ok(postService.createPost(session, requestDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<?>> findAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        log.info("게시물 전체 조회 성공");
        return ResponseEntity.ok(ok(postService.findAllPost(page, size)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<?>> findPostById(@PathVariable Long postId) {

        log.info("게시물 단건 조회 성공");
        return ResponseEntity.ok(ok(postService.findPostById(postId)));

    }

    /*게시물 전체 조회*/
    @GetMapping("/sorted-by-created")
    public ResponseEntity<ApiResponseDto<?>> findPostsSortedByModified(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        log.info("기간별 게시물 전체 조회 성공");
        return ResponseEntity.ok(ok(postService.findPostsSortedByModifiedAt(startDate, endDate, page, size)));
    }

    // 포스트 업데이트
    @LoginRequired
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<?>> updatePostImageAndContents(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {

        log.info("게시물 수정 성공");
        return ResponseEntity.ok(ok(postService.updatePostImageAndContents(postId, session.getId(), dto)));
    }

    // 게시물 삭제
    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<?>> deletePost(
            @PathVariable Long postId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        postService.deletePost(postId, session.getId());

        log.info("게시물 삭제 성공");
        return ResponseEntity.ok(ok(null));
    }
}