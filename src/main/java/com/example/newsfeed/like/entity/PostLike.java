package com.example.newsfeed.like.entity;

import com.example.newsfeed.follow.entity.FollowStatus;
import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_like")
public class PostLike extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void updateLikeStatus(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
    }

}
