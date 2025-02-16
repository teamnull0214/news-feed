package com.example.newsfeed.post.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/members/{memberId}/posts")
    public ResponseEntity<PostResponseDto> createPost(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @RequestBody PostRequestDto requestDto
    ) {

        log.info("게시물 생성 API 호출");
        return ResponseEntity.ok(postService.createPost(session, requestDto));
    }

}
