package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String contents;

    public PostRequestDto(String contents) {
        this.contents = contents;
    }
}
