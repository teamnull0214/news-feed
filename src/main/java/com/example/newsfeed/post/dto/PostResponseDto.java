package com.example.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final Member member;
    private String image;
    private String contents;

    public PostResponseDto(Long id, Member member, String image, String contents) {
        this.id = id;
        this.member = member;
        this.image = image;
        this.contents;
        }
    }

}
