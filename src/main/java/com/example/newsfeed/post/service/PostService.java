package com.example.newsfeed.post.service;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(SessionMemberDto session, PostRequestDto dto) {

        Member member = new Member(session.getId());
        Post post = new Post(dto.getContents(), dto.getImage(), member);
        postRepository.save(post);

        log.info("게시물 생성 성공");
        return PostResponseDto.toDto(post, session);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPost() {
        return postRepository.findAll().stream().map(PostResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long postId) {
        Post findPost = findPostByIdOrElseThrow(postId);
        return PostResponseDto.toDto(findPost);
    }

    @Transactional
    public PostResponseDto updateImageAndContents(Long postId, Long memberId, PostRequestDto dto) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("다른사람이 작성한 게시물이라 수정못함");
        }

        if (dto.getImage() != null) {
            findPost.updateImage(dto.getImage());
        }
        if (dto.getContents() != null) {
            findPost.updateContents(dto.getContents());
        }
        return PostResponseDto.toDto(findPost);
    }

    //삭제
    @Transactional
    public void deletePost(Long postId, Long memberId) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("해당 사용자 ID 찾을 수 없음");
        }
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("해당 ID 찾을 수 없음");
        }

        postRepository.deleteById(postId);
    }

    public Post findPostByIdOrElseThrow(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 ID 찾을 수 없음")
        );
    }
}
