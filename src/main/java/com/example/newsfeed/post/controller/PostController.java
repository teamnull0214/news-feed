package com.example.newsfeed.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            RequestBody PostRequestDto dto
    ) {
        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable Long id) {
        postService.deleteById(id);
    }
}
