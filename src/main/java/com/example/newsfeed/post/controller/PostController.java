package com.example.newsfeed.post.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.post.dto.PostRequestDto;
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
    public ResponseEntity<PostResponseDto> createPost(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @Valid @RequestBody PostRequestDto requestDto
    ) {
        return ResponseEntity.ok(postService.createPost(session, requestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAllPosts() {
        List<PostResponseDto> postResponseDtoList = postService.findAllPost();
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.findPostById(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 게시물 업데이트
    @LoginRequired
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updateImageAndContents(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto dto,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        PostResponseDto postResponseDto = postService.updateImageAndContents(postId, session.getId(), dto);
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시물 삭제
    @LoginRequired
    @DeleteMapping("/{postId}")
    public void deletePost(
            @PathVariable Long postId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        postService.deletePost(postId, session.getId());
    }
}