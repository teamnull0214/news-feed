package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String content;
    private final String image;

    public PostRequestDto(String content, String image) {
        this.content = content;
        this.image = image;
    }
}
