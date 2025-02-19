package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.member.dto.response.MemberListGetResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.constant.EntityConstants.MODIFIED_AT;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowListService {

    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public Page<MemberListGetResponseDto> findAllFollowerMembers(Long id, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Follow> followPage = followRepository.findByFollowerIdAndStatus(id, pageable);

        List<MemberListGetResponseDto> dtoList = followPage.stream()
                .map(Follow::getFollowingMember)
                .filter(member -> !member.isDeleted())
                .map(member -> {
                    int followerCount = countByFollowingMemberId(member.getId());
                    return new MemberListGetResponseDto(member, followerCount);
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, followPage.getTotalElements());
    }

    public int countByFollowingMemberId(Long memberId) {
        return followRepository.countByFollowingMemberId(memberId);
    }
}
