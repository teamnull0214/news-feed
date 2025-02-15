package com.example.newsfeed.post.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostResponseDto createPost(Long memberId,PostRequestDto requestDto) {

        // 유저 확인
        Member member = memberRepository.findMemberById(memberId).orElseThrow(
                () -> new RuntimeException("id에 맞는 멤버가 없습니다.")
        );

        // 게시물 생성
        Post post = new Post(requestDto.getImage(), requestDto.getContents(), member);
        Post savedPost = postRepository.save(post);

        log.info("게시물 등록 성공");

        return new PostResponseDto(
                savedPost.getId(),
                member.getNickname(),
                savedPost.getImage(),
                LocalDateTime.now(),
                LocalDateTime.now()
                );
    }
}
