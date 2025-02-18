package com.example.newsfeed.like.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LikeStatus {
    NOT_LIKE(false),
    LIKE(true);

    private final boolean status;
}
