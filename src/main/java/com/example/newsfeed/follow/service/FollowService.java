package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.entity.FollowStatus;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.member.dto.MemberListResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;

    @Transactional
    public void createFollow(Long followerId, Long followingId) {

        if (followingId.equals(followerId)) {
            throw new RuntimeException("본인을 팔로우 할 수 없다.");
        }

        // 팔로워와 팔로잉을 조회
        Member followerMember = memberService.findActiveMemberByIdOrElseThrow(followerId);
        Member findFollowingMember = memberService.findActiveMemberByIdOrElseThrow(followingId);

        if (followRepository.existsFollowByFollowerMemberAndFollowingMember(followerMember, findFollowingMember)) {
            throw new RuntimeException("이미 팔로우를 한 유저 입니다.");
        }

        try {
            Follow follow = new Follow(followerMember, findFollowingMember);       // followStatus 를 true 로 자동 생성
            followRepository.save(follow);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("이미 팔로우를 한 유저 입니다.");
        }
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followingId) {
        if (followingId.equals(followerId)) {
            throw new RuntimeException("본인을 언팔로우 할 수 없다.");
        }

        // 팔로워와 팔로잉을 조회
        Member followerMember = memberService.findActiveMemberByIdOrElseThrow(followerId);
        Member findFollowingMember = memberService.findActiveMemberByIdOrElseThrow(followingId);

        Follow findFollow = findFollowByFollowerMemberAndFollowingMemberOrElseThrow(followerMember, findFollowingMember);

        if (findFollow.getFollowStatus() == FollowStatus.NOT_FOLLOWING) {
            throw new RuntimeException("이미 언팔로우를 한 유저 입니다.");
        }

        findFollow.updateFollowStatusFalse();
    }

    public List<MemberListResponseDto> findAllFollowerMembers(Long id) {
        List<Follow> followList = followRepository.findByFollowerMemberId(id);

        return followList.stream()
                .filter(follow -> follow.getFollowStatus() == FollowStatus.FOLLOWING)
                .map(follow -> new MemberListResponseDto(follow.getFollowingMember()))
                .toList();
    }

    private Follow findFollowByFollowerMemberAndFollowingMemberOrElseThrow(Member followerMember, Member followingMember) {
        return followRepository.findFollowByFollowerMemberAndFollowingMember(followerMember, followingMember).orElseThrow(
                () -> new RuntimeException("팔로우 내용을 찾을 수 없습니다.")
        );
    }
}
