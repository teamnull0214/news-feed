package com.example.newsfeed.post.service;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service("postService")
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public PostCreateResponseDto createPost(SessionMemberDto session, PostCreateRequestDto requestDto) {
        Member member = memberService.findActiveMemberByIdOrElseThrow(session.getId());

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

    /*todo: 페이징을 통해 정렬만 진행 하시면 될 것 같아요.*/
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsSortedByModifiedAt(LocalDate startDate, LocalDate endDate) {

        return postRepository.findAllByModifiedAt(startDate, endDate)
                .stream()
                .map(PostResponseDto::toDto)
                .toList();
    }

    /*todo: 등록일과 좋아요에 대한 부분도 동일하게 진행하면 될 것같습니다.*/

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

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("해당 사용자 ID 찾을 수 없음");
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
