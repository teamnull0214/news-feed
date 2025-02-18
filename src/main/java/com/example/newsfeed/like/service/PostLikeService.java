package com.example.newsfeed.like.service;

import com.example.newsfeed.like.entity.LikeStatus;
import com.example.newsfeed.like.entity.PostLike;
import com.example.newsfeed.like.repository.PostLikeRepository;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService implements LikeService{

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final MemberService memberService;

    @Override
    @Transactional
    public void createLike(Long memberId, Long postId) {

        Post findPost = postService.findPostByIdOrElseThrow(postId);

        if (findPost.getMember().getId().equals(memberId)) {
            throw new RuntimeException("본인이 작성한 게시글에 좋아요를 누를 수 없다.");
        }

        // post-like 테이블에 좋아요 기록이 있는지 없는지 확인
        // 있을때 상태가 true 이다 --> 이미 좋아요를 누른 상태
        // 있을때 상태가 false 이다 --> 좋아요를 해제한 상태(좋아요를 누를 수 있음)
        // 없을때 --> 처음 좋아요를 누른 상태 (좋아요를 누를 수 있음)

        Optional<PostLike> optionalPostLike = postLikeRepository.findPostLikeByMember_IdAndPost_Id(memberId, postId);

        if (optionalPostLike.isPresent()) {
            PostLike findPostLike = optionalPostLike.get();

            if (findPostLike.getLikeStatus() == LikeStatus.LIKE) {
                throw new RuntimeException("이미 좋아요를 누른 상태입니다.");
            }

            findPostLike.updateLikeStatus(LikeStatus.LIKE);
            return;
        }

        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);

        PostLike postLike = new PostLike(findMember, findPost);
        postLikeRepository.save(postLike);
    }

    @Override
    @Transactional
    public void deleteLike(Long memberId, Long postId) {

        Post findPost = postService.findPostByIdOrElseThrow(postId);

        if (findPost.getMember().getId().equals(memberId)) {
            throw new RuntimeException("본인이 작성한 게시글에 좋아요를 해제할 수 없다.");
        }

        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);

        PostLike findPostLike = findPostLikeByMember_IdAndPost_IdOrElseThrow(findMember.getId(), findPost.getId());

        if (findPostLike.getLikeStatus() == LikeStatus.NOT_LIKE) {
            throw new RuntimeException("이미 좋아요 해제를 한 게시물 입니다.");
        }

        findPostLike.updateLikeStatus(LikeStatus.NOT_LIKE);
    }

    private PostLike findPostLikeByMember_IdAndPost_IdOrElseThrow(Long memberId, Long postId) {
        return postLikeRepository.findPostLikeByMember_IdAndPost_Id(memberId, postId).orElseThrow(
                () -> new RuntimeException("좋아요 기록 존재하지 않음")
        );
    }
}