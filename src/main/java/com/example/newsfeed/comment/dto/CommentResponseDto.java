package com.example.newsfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long postId;
    private final String nickname;
    private final String email;
    private final String commentContents;
    //private final Long likeCount; // like 기능이 만들어지면 구현
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Long id, Long postId, String nickname, String email, String commentContents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.postId = postId;
        this.nickname = nickname;
        this.email = email;
        this.commentContents = commentContents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

