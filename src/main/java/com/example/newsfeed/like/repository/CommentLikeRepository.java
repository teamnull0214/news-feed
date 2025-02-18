package com.example.newsfeed.like.repository;

import com.example.newsfeed.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);
}
