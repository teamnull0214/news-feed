package com.example.newsfeed.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdateRequestDto {

    private String contents;
    private String image;

}
