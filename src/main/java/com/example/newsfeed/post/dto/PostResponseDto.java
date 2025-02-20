package com.example.newsfeed.post.dto;

import com.example.newsfeed.global.dto.SessionMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

import com.example.newsfeed.post.entity.Post;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    // feat/post-create 브랜치에서 만든 변수
    private final Long id;
    private final String nickname;
    private final String email;
    private final String contents;
    private final String image;
    private final int likeCount;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    // 다건/단건 조회
    public static PostResponseDto toDto(Post post){
        return new PostResponseDto(
                post.getId(),
                post.getMember().getNickname(),
                post.getMember().getEmail(),
                post.getContents(),
                post.getImage(),
                post.getPostLikeList().size(),
                post.getCommentList().size(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    // 게시물 생성
    public static PostResponseDto toDto(Post post, SessionMemberDto dto){
        return new PostResponseDto(
                post.getId(),
                dto.getNickname(),
                dto.getEmail(),
                post.getContents(),
                post.getImage(),
                post.getPostLikeList().size(),
                post.getCommentList().size(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
