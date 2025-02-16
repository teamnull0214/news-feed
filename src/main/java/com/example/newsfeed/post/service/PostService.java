package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.repository.PostRepository;




@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /*
    feat/post-read 브랜치
    모든 작성글을 찾는 서비스 JpaRepository에 스트림
    */
    public List<PostResponseDto> findAll(){

        return postRepository.findAll().stream().map(PostResponseDto::toDto).toList();
    }





}
