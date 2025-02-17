package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostCreateRequestDto {
    private final String contents;
    private final String image;

    public PostCreateRequestDto(String contents, String image) {
        this.contents = contents;
        this.image = image;
    }
}
