package com.example.newsfeed.follow.entity;

// 친구상태로의 코드 확장성을 고려하기위해 enum 자료형으로 팔로우 상태 관리
public enum FollowStatus {
    NOT_FOLLOWING(false),
    FOLLOWING(true);

    FollowStatus(boolean status) {
    }
}
