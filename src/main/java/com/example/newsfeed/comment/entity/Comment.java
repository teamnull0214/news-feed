package com.example.newsfeed.comment.entity;

import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(Post post, Member member, String commentContents) {
        this.post = post;
        this.member = member;
        this.commentContents = commentContents;
    }

    public void updateComment(String commentContents) {
        this.commentContents = commentContents;
    }

}
