package com.example.newsfeed.post.entity;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.global.entity.BaseDateTime;
import com.example.newsfeed.like.entity.PostLike;
import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String image;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    public Post(String contents, String image, Member member) {
        this.contents = contents;
        this.image = image;
        this.member = member;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
    public void updateImage(String image) {
        this.image = image;
    }
}