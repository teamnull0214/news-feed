package com.example.newsfeed.post.service;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.global.exception.custom.NotFoundException;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.dto.PostCreateRequestDto;
import com.example.newsfeed.post.dto.PostCreateResponseDto;
import com.example.newsfeed.post.dto.PostUpdateRequestDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.example.newsfeed.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.newsfeed.global.exception.ErrorCode.POST_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostCreateResponseDto createPost(SessionMemberDto session, PostCreateRequestDto requestDto) {
        Member member = memberRepository.findMemberById(session.getId()).orElseThrow(
                () -> new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND)
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
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream().map(PostResponseDto::toDto).toList();
    }


    /*
    feat/post-read 브랜치
    postId를 통해 특정 게시물을 찾는(조회하는) 서비스
    */
    public PostResponseDto findById(Long postId) {

        Post findPost = postRepository.findByIdOrElseThrow(postId);
        return PostResponseDto.toDto(findPost);
    }

    //feat/post-updateDelete
    // 업데이트
    @Transactional
    public PostResponseDto updateImageAndContents(Long postId, Long memberId, PostUpdateRequestDto dto) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (Objects.equals(memberId, findPostMembers.getId())) {
            throw new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND);
        }

        if (dto.getImage() != null) {
            findPost.updateImage(dto.getImage());
        }

        if (dto.getContents() != null) {
            findPost.updateContents(dto.getContents());
        }
        return PostResponseDto.toDto(findPost);
    }

    //feat/post-updateDelete
    //삭제
    @Transactional
    public void deletePost(Long postId, Long memberId) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (Objects.equals(memberId, findPostMembers.getId())) {
            throw new NotFoundException.MemberNotFoundException(MEMBER_NOT_FOUND);
        }
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException.PostNotFoundException(POST_NOT_FOUND);
        }

        postRepository.deleteById(postId);
    }

    private Post findPostByIdOrElseThrow(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException.PostNotFoundException(POST_NOT_FOUND)
        );
    }

}
