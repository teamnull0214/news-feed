package com.example.newsfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreateResponseDto {
    private final Long id;
    private final Long memberId;
    private final String nickname;
    private final String image;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostCreateResponseDto(Long id, Long memberId, String nickname, String image, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.memberId = memberId;
        this.nickname = nickname;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
