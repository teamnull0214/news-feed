package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String contents;
    private final String image;

    public PostRequestDto(String contents, String image) {
        this.contents = contents;
        this.image = image;
    }
}
