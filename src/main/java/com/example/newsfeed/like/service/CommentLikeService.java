package com.example.newsfeed.like.service;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.global.exception.custom.BadRequestException;
import com.example.newsfeed.global.exception.custom.ConflictException;
import com.example.newsfeed.global.exception.custom.NotFoundException;
import com.example.newsfeed.like.entity.CommentLike;
import com.example.newsfeed.like.repository.CommentLikeRepository;
import com.example.newsfeed.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.newsfeed.global.exception.ErrorCode.*;
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
        Optional<CommentLike> findCommentLike = commentLikeRepository.findByMemberIdAndCommentId(memberId, findComment.getId());

        /* 좋아요 기록이 있는 경우 */
        if (findCommentLike.isPresent()) {
            CommentLike commentLike = findCommentLike.get();

            /* 이미 좋아요가 눌려있는 경우 */
            if (commentLike.getLikeStatus() == LIKE) {
                throw new ConflictException.AlreadyLikedException(ALREADY_LIKED);
            }

            /* 이미 존재하는 좋아요를 업데이트한 경우, 새로 생성할 필요 없음 */
            commentLike.updateCommentLike(LIKE);
            return;
        }

        /* 좋아요가 눌려있지 않은 경우 (최초 좋아요 등록) */
        CommentLike commentLike = new CommentLike(new Member(memberId), findComment);
        commentLikeRepository.save(commentLike);
    }

    @Override
    @Transactional
    public void deleteLike(Long memberId, Long commentId) {
        Comment findComment = validateNotCommentAuthor(memberId, commentId);
        CommentLike findCommentLike = findByMemberAndCommentOrElseThrow(memberId, findComment.getId());

        if (findCommentLike.getLikeStatus() == NOT_LIKE) {
            throw new ConflictException.AlreadyUnLikedException(ALREADY_UNLIKED);
        }

        findCommentLike.updateCommentLike(NOT_LIKE);
    }

    private Comment validateNotCommentAuthor(Long memberId, Long commentId) {
        Comment findComment = commentService.findCommentByIdOrElseThrow(commentId);

        /* 본인이 작성한 댓글에 좋아요를 남길 수 없습니다. */
        if (findComment.getMember().getId().equals(memberId)) {
            throw new BadRequestException.CannotLikeOwnEntityException(CANNOT_LIKE_OWN_ENTITY);
        }
        return findComment;
    }

    private CommentLike findByMemberAndCommentOrElseThrow(Long memberId, Long commentId) {
        return commentLikeRepository.findByMemberIdAndCommentId(
                memberId, commentId).orElseThrow(() -> new NotFoundException.LikeRecordNotFoundForEntityException(LIKE_RECORD_NOT_FOUND));
    }
}
