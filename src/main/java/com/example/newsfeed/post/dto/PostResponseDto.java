package com.example.newsfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String nickname;
    private final String image;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Long id, String nickname, String image, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
