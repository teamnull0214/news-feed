package com.example.newsfeed.post.service;

import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.dto.PostCreateRequestDto;
import com.example.newsfeed.post.dto.PostCreateResponseDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.member.entity.Member;



@Slf4j
@Service
@RequiredArgsConstructor
public class PostService extends BaseDateTime {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostCreateResponseDto createPost(SessionMemberDto session, PostCreateRequestDto requestDto) {
        Member member = memberRepository.findMemberById(session.getId()).orElseThrow(
                () -> new RuntimeException("id에 맞는 멤버가 없습니다.")
        );
        Member findMember = Member.fromMemberId(session.getId());
        Post post = new Post(requestDto.getImage(), requestDto.getContents(), findMember);
        Post savedPost = postRepository.save(post);

        log.info("게시물 생성 성공");
        return new PostCreateResponseDto(
                savedPost.getId(),
                member.getNickname(),
                savedPost.getContents(),
                savedPost.getImage(),
                savedPost.getCreatedAt(),
                savedPost.getModifiedAt()
        );
    }


    /*
    feat/post-read 브랜치
    모든 작성글을 찾는 서비스 JpaRepository에 스트림
    */
    public List<PostResponseDto> findAll(){

        return postRepository.findAll().stream().map(PostResponseDto::toDto).toList();
    }


    /*
    feat/post-read 브랜치
    postId를 통해 특정 게시물을 찾는(조회하는) 서비스
    */
    public PostResponseDto findById(Long postId) {

        Post findPost = postRepository.findByIdOrElseThrow(postId);
        Member writer = findPost.getMember(); // Post 클래스의 멤버변수 private Member member에 접근!!

        return new PostResponseDto(findPost.getId(), writer.getNickname(), findPost.getContents(), findPost.getImage(), findPost.getCreatedAt(), findPost.getModifiedAt());
    }





}
