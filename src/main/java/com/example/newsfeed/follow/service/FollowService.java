package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.global.exception.custom.BadRequestException;
import com.example.newsfeed.global.exception.custom.ConflictException;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.newsfeed.global.exception.ErrorCode.ALREADY_FOLLOWING;
import static com.example.newsfeed.global.exception.ErrorCode.CANNOT_FOLLOW_SELF;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;

    @Transactional
    public void createFollow(Long followerId, Long followingId) {

        if (followingId.equals(followerId)) {
            throw new BadRequestException.CannotFollowSelfException(CANNOT_FOLLOW_SELF);
        }

        // 팔로워와 팔로잉을 조회
        Member followerMember = memberService.findMemberByIdOrElseThrow(followerId);
        Member findFollowingMember = memberService.findActiveMemberByIdOrElseThrow(followingId);

        if (followRepository.existsFollowByFollowerMemberAndFollowingMember(followerMember, findFollowingMember)) {
            throw new ConflictException.AlreadyFollowing(ALREADY_FOLLOWING);
        }

        try {
            Follow follow = new Follow(followerMember, findFollowingMember);       // followStatus 를 true 로 자동 생성
            followRepository.save(follow);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException.AlreadyFollowing(ALREADY_FOLLOWING);
        }
    }
}
