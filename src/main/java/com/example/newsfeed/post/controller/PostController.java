package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /*
    feat/post-read 브랜치
    member들이 작성한 모든 글을 조회하는 메서드
    예외처리 추가하기 -> 현재 기본기능만 구현되어있음
    */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAllPosts(){

        List<PostResponseDto> postResponseDtoList = postService.findAll();

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK); // status 200
    }

}
