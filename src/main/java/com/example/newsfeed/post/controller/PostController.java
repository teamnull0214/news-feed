package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.PostUpdateRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.post.dto.PostCreateRequestDto;
import com.example.newsfeed.post.dto.PostCreateResponseDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성 (로그인 상태)
    @LoginRequired
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @Valid @RequestBody PostCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(postService.createPost(session, requestDto));
    }

    /*
    feat/post-read 브랜치
    postId를 통해 특정 게시물을 조회하는 메서드
    */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long postId) {

        PostResponseDto postResponseDto = postService.findById(postId);

        // 성공시 status 200 실패시 stauts 404 PostRepository.java ->  findByIdOrElseThrow 메서드 참고
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK); // status 200
    }

    //feat/post-updateDelete
    // 포스트 업데이트
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updateImageAndContents(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDto dto,
            HttpServletRequest httpServletRequest
    ) {
        Long memberId = getMemberIdBySession(httpServletRequest);
        PostResponseDto postResponseDto = postService.updateImageAndContents(postId, memberId, dto);
        return ResponseEntity.ok(postResponseDto);
    }

    //feat/post-updateDelete
    //삭제
    @DeleteMapping("/{postId}")
    public void deletePost(
            @PathVariable Long postId,
            HttpServletRequest httpServletRequest
    ) {
        Long memberId = getMemberIdBySession(httpServletRequest);
        postService.deletePost(postId, memberId);
    }

    private Long getMemberIdBySession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        return (Long) session.getAttribute("memberId");
    }

}