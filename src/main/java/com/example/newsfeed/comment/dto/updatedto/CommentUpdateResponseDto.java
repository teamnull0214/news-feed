package com.example.newsfeed.comment.dto.updatedto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {

    private final Long id;
    private final String postContents;
    private final String image;
    private final String nickname;
    private final String email;
    private final String commentContents;
    //private final Long likeCount; // like 기능이 만들어지면 구현
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentUpdateResponseDto(Long id, String postContents, String image, String nickname, String email, String commentContents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.postContents = postContents;
        this.image = image;
        this.nickname = nickname;
        this.email = email;
        this.commentContents = commentContents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

