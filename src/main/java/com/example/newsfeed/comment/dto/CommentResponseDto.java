package com.example.newsfeed.comment.dto;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private final Long id;
    private final Long postId;
    private final String nickname;
    private final String email;
    private final String commentContents;
    private final int likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    // read시 사용하는 mapper
    public static CommentResponseDto toDto(Comment comment){
        Member writer = comment.getMember();
        Post post = comment.getPost();
        return new CommentResponseDto(
                comment.getId(),
                post.getId(),
                writer.getNickname(),
                writer.getEmail(),
                comment.getCommentContents(),
                comment.getCommentLikeList().size(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 세션을 사용하여 responseDto 매핑(create, update)
    public static CommentResponseDto toDto(Comment comment, SessionMemberDto session){
        Post post = comment.getPost();
        return new CommentResponseDto(
                comment.getId(),
                post.getId(),
                session.getNickname(),
                session.getEmail(),
                comment.getCommentContents(),
                comment.getCommentLikeList().size(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}

