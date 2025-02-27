package com.example.newsfeed.like.service;

import com.example.newsfeed.global.exception.custom.BadRequestException;
import com.example.newsfeed.global.exception.custom.ConflictException;
import com.example.newsfeed.global.exception.custom.NotFoundException;
import com.example.newsfeed.like.entity.LikeStatus;
import com.example.newsfeed.like.entity.PostLike;
import com.example.newsfeed.like.repository.PostLikeRepository;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.newsfeed.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostLikeService implements LikeService{

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Override
    @Transactional
    public void createLike(Long memberId, Long postId) {

        Post findPost = validateNotPostAuthor(memberId, postId);
        Optional<PostLike> optionalPostLike = postLikeRepository.findByMemberIdAndPostId(memberId, findPost.getId());

        /*
        post-like 테이블에 좋아요 기록이 있는지 없는지 확인
        (1) 있을 때 상태가 true 이다 --> 이미 좋아요를 누른 상태
        (2) 있을 때 상태가 false 이다 --> 좋아요를 해제한 상태 (좋아요를 누를 수 있음)
        (3) 없을 때 --> 처음 좋아요를 누른 상태 (좋아요를 누를 수 있음)
         */
        if (optionalPostLike.isPresent()) {
            PostLike findPostLike = optionalPostLike.get();

            if (findPostLike.getLikeStatus() == LikeStatus.LIKE) {
                throw new ConflictException.AlreadyLikedException(ALREADY_LIKED);    // (1)
            }
            findPostLike.updateLikeStatus(LikeStatus.LIKE);             // (2)
            return;
        }
        PostLike postLike = new PostLike(new Member(memberId), findPost);
        postLikeRepository.save(postLike);                              // (3)
    }

    @Override
    @Transactional
    public void deleteLike(Long memberId, Long postId) {

        Post findPost = validateNotPostAuthor(memberId, postId);

        /*
        post-like 테이블에 좋아요 기록이 있는지 없는지 확인
        (1) 있을 때 상태가 true 이다 --> 이미 좋아요를 누른 상태 (좋아요 취소 가능)
        (2) 있을 때 상태가 false 이다 --> 좋아요를 해제한 상태
        (3) 없을 때 --> 좋아요를 한 적이 없음
         */
        PostLike findPostLike = findByMemberAndPostOrElseThrow(memberId, findPost.getId());   // (3)

        if (findPostLike.getLikeStatus() == LikeStatus.NOT_LIKE) {
            throw new ConflictException.AlreadyUnLikedException(ALREADY_UNLIKED);               // (2)
        }
        findPostLike.updateLikeStatus(LikeStatus.NOT_LIKE);                             // (1)
    }

    private Post validateNotPostAuthor(Long memberId, Long postId) {
        Post findPost = postService.findPostByIdOrElseThrow(postId);

        if (findPost.getMember().getId().equals(memberId)) {
            throw new BadRequestException.CannotLikeOwnEntityException(CANNOT_LIKE_OWN_ENTITY);
        }
        return findPost;
    }

    private PostLike findByMemberAndPostOrElseThrow(Long memberId, Long postId) {
        return postLikeRepository.findByMemberIdAndPostId(memberId, postId).orElseThrow(
                () -> new NotFoundException.LikeRecordNotFoundForEntityException(LIKE_RECORD_NOT_FOUND)
        );
    }
}