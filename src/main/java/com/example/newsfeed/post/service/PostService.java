package com.example.newsfeed.post.service;

import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.newsfeed.global.constant.EntityConstants.MODIFIED_AT;

@Slf4j
@Service("postService")
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
    public Page<PostResponseDto> findAllPost(int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostResponseDto> dtoList = postPage.getContent().stream()
                .map(PostResponseDto::toDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long postId) {
        Post findPost = findPostByIdOrElseThrow(postId);
        return PostResponseDto.toDto(findPost);
    }

    /* 수정일 조건 기준 게시글 전체조회 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostsSortedByModifiedAt(LocalDate startDate, LocalDate endDate, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Post> postPage = postRepository.findAllByModifiedAt(startDate, endDate, pageable);

        List<PostResponseDto> dtoList = postPage.getContent().stream()
                .map(PostResponseDto::toDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());
    }

    @Transactional
    public PostResponseDto updatePostImageAndContents(Long postId, Long memberId, PostRequestDto dto) {

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
