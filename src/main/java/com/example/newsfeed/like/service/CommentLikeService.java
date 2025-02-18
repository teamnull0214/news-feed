package com.example.newsfeed.like.service;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.like.entity.CommentLike;
import com.example.newsfeed.like.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.newsfeed.like.entity.LikeStatus.LIKE;
import static com.example.newsfeed.like.entity.LikeStatus.NOT_LIKE;

@Service
@RequiredArgsConstructor
public class CommentLikeService implements LikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    @Override
    @Transactional
    public void createLike(Long memberId, Long commentId) {
        Comment findComment = validateNotCommentAuthor(memberId, commentId);
        CommentLike findCommentLike = findByMemberAndCommentOrElseThrow(memberId, findComment.getId());

        /* 이미 좋아요가 눌려있는 경우 */
        if (findCommentLike.getLikeStatus() == LIKE) {
            throw new RuntimeException("이미 좋아요를 누른 상태입니다.");
        }

        /* 좋아요가 눌려있지 않은 경우 */
        findCommentLike.updateCommentLike(LIKE);  // 기존 좋아요 상태를 업데이트

        // 이미 존재하는 좋아요를 업데이트한 경우, 새로 생성할 필요 없음
        commentLikeRepository.save(findCommentLike);
    }

    @Override
    @Transactional
    public void deleteLike(Long memberId, Long commentId) {
        Comment findComment = validateNotCommentAuthor(memberId, commentId);
        CommentLike findCommentLike = findByMemberAndCommentOrElseThrow(memberId, findComment.getId());

        if (findCommentLike.getLikeStatus() == NOT_LIKE) {
            throw new RuntimeException("이미 좋아요 해제를 한 댓글입니다.");
        }

        findCommentLike.updateCommentLike(NOT_LIKE);
    }

    private Comment validateNotCommentAuthor(Long memberId, Long commentId) {
        Comment findComment = commentService.findCommentByIdOrElseThrow(commentId);

        /* 본인이 작성한 댓글에 좋아요를 남길 수 없습니다. */
        if (findComment.getMember().getId().equals(memberId)) {
            throw new RuntimeException("본인이 작성한 댓글에 좋아요/좋아요 취소를 누를 수 없다.");
        }
        return findComment;
    }

    private CommentLike findByMemberAndCommentOrElseThrow(Long memberId, Long commentId) {
        return commentLikeRepository.findByMemberIdAndCommentId(
                memberId, commentId).orElseThrow(() -> new RuntimeException("좋아요 기록이 존재하지 않음"));
    }
}
