package com.example.newsfeed.like.entity;

import com.example.newsfeed.follow.entity.FollowStatus;
import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name = "comment_like")
//public class CommentLike extends BaseDateTime {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member Member;
//
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private Comment comment;
//
//    @Enumerated(EnumType.STRING)
//    private LikeStatus likeStatus;
//
//}
