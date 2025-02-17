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

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updateImageAndContent(
            @PathVariable Long id,
            @valid @RequestBody PostRequestDto dto
            HttpServletRequest httpServletRequest
    ) {
        Long memberId = getMemberIdBySession(httpServletRequest);
        PostResponseDto postResponseDto = postService.updateImageAndContent(id, memberId, dto);
        return ResponseEntity.ok(postService.updateImageAndContent(id, memberId, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
            ) {
        Long userId = getMemberIdBySession(httpServletRequest);
        postService.deleteById(id);
    }

    private Long getMemberIdBySession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        return (Long) session.getAttribute("memberId");
    }
}
