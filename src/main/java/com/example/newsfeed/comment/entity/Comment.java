package com.example.newsfeed.comment.entity;

import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.like.entity.CommentLike;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(Post post, Member member, String commentContents) {
        this.post = post;
        this.member = member;
        this.commentContents = commentContents;
    }

    public void updateComment(String commentContents) {
        this.commentContents = commentContents;
    }

    public Comment(Long id) {
        this.id = id;
    }

}
