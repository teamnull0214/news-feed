package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final Member member;
    private String image;
    private String content;

    public PostResponseDto(Long id, Member member, String image, String content) {
        this.id = id;
        this.member = member;
        this.image = image;
        this.content;
        }
    }

}
