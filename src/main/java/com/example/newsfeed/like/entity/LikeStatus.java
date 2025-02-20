package com.example.newsfeed.like.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeStatus {
    NOT_LIKE(false),
    LIKE(true);

    private final boolean status;
}
