package com.example.newsfeed.follow.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FollowStatus {
    NOT_FOLLOWING(false),
    FOLLOWING(true);

    private final boolean status;
}