package com.example.newsfeed.like.repository;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findPostLikeByMember_IdAndPost_Id(Long memberId, Long postId);
}
