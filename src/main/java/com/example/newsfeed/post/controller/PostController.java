package com.example.newsfeed.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.example.newsfeed.global.constant.EntityConstants.LOGIN_MEMBER;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    // 게시물 생성 (로그인 상태)
//    @LoginRequired
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<PostResponseDto> createPost(
//            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
////            @RequestPart("post") @Valid PostRequestDto requestDto,
//            @RequestPart(value = "image", required = false) MultipartFile image
//    ) throws IOException {
//        return ResponseEntity.ok(postService.createPost(session, null, image));
//    }

    @PostMapping(value = "/upload",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity upload(
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session,
            @RequestPart @Valid PostRequestDto request
//            @RequestPart("file") MultipartFile file)
    ) throws IOException{
//        return ResponseEntity.ok(postService.upload(session, request, file));
//        return ResponseEntity.ok(postService.upload(session, request));

        return null;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> postResponseDtoList = postService.findAllPost(page, size);
        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.findPostById(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    /*게시물 전체 조회*/
    @GetMapping("/sorted-by-created")
    public ResponseEntity<Page<PostResponseDto>> findPostsSortedByModified(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> postResponseDtoList = postService.findPostsSortedByModifiedAt(startDate, endDate, page, size);
        return ResponseEntity.ok(postResponseDtoList);
    }

    // 포스트 업데이트
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostImageAndContents(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto dto,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        PostResponseDto postResponseDto = postService.updatePostImageAndContents(postId, session.getId(), dto);
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시물 삭제
    @LoginRequired
    @DeleteMapping("/{postId}")
    public void deletePost(
            @PathVariable Long postId,
            @SessionAttribute(name = LOGIN_MEMBER) SessionMemberDto session
    ) {
        postService.deletePost(postId, session.getId());
    }
}