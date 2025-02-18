package com.example.newsfeed.like.entity;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLike extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    public CommentLike(Member Member, Comment comment) {
        this.member = Member;
        this.comment = comment;
        this.likeStatus = LikeStatus.LIKE;
    }

    public void updateCommentLike(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
    }
}
