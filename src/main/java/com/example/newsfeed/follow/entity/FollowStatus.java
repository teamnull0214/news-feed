package com.example.newsfeed.follow.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 친구상태로의 코드 확장성을 고려하기위해 enum 자료형으로 팔로우 상태 관리
@Getter
@RequiredArgsConstructor
public enum FollowStatus {
    NOT_FOLLOWING(false),
    FOLLOWING(true);

    private final boolean status;
}
