package com.example.newsfeed.follow.repository;

import com.example.newsfeed.follow.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    int countByFollowingMemberId(Long id);

    @Query("SELECT f FROM Follow f WHERE f.followerMember.id = :followerId AND f.followingMember.id = :followingId")
    Optional<Follow> findByFollowerIdAndFollowingId(
            @Param("followerId") Long followId,
            @Param("followingId") Long followingId
    );

    @Query("SELECT f FROM Follow f WHERE f.followerMember.id = :followerId AND f.followStatus = 'FOLLOWING'")
    Page<Follow> findByFollowerIdAndStatus(@Param("followerId") Long followerId, Pageable pageable);
}
