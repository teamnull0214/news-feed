package com.example.newsfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreateResponseDto {
    private final Long id;
    private final String nickname;
    private final String image;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostCreateResponseDto(Long id, String nickname, String image, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
