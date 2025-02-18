package com.example.newsfeed.follow.repository;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsFollowByFollowerMemberAndFollowingMember(Member followerMember, Member findFollowingMember);
}
