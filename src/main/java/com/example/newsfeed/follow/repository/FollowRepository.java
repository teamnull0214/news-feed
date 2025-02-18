package com.example.newsfeed.follow.repository;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsFollowByFollowerMemberAndFollowingMember(Member followerMember, Member findFollowingMember);

    Optional<Follow> findFollowByFollowerMemberAndFollowingMember(Member followerMember, Member findFollowingMember);

    List<Follow> findByFollowerMemberId(Long id);
}
