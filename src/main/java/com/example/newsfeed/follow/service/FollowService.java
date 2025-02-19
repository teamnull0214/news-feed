package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.entity.FollowStatus;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        validateEqualsFollowerAndFollowing(followerId, followingId);

        Optional<Follow> optionalFollow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);

        if (optionalFollow.isPresent()) {
            Follow findFollow = optionalFollow.get();

            if (findFollow.getFollowStatus() == FollowStatus.FOLLOWING) {
                throw new RuntimeException("이미 팔로우를 한 상태입니다.");
            }
            findFollow.updateFollowStatus(FollowStatus.FOLLOWING);
            return;
        }

        Follow follow = new Follow(new Member(followerId), new Member(followingId));
        followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followingId) {

        validateEqualsFollowerAndFollowing(followerId, followingId);

        Follow findFollow = findByFollowerIdAndFollowingIdOrElseThrow(followerId, followingId);

        if (findFollow.getFollowStatus() == FollowStatus.NOT_FOLLOWING) {
            throw new RuntimeException("이미 언팔로우를 한 유저 입니다.");
        }
        findFollow.updateFollowStatus(FollowStatus.NOT_FOLLOWING);
    }

    private Follow findByFollowerIdAndFollowingIdOrElseThrow(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId).orElseThrow(
                () -> new RuntimeException("팔로우 내용을 찾을 수 없습니다.")
        );
    }

    private void validateEqualsFollowerAndFollowing(Long followerId, Long followingId) {
        memberService.findActiveMemberByIdOrElseThrow(followingId);

        if (followingId.equals(followerId)) {
            throw new BadRequestException.CannotFollowSelfException(CANNOT_FOLLOW_SELF);
        }
    }
}
