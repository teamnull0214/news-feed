package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.like.service.LikeService;
import com.example.newsfeed.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController implements LikeController{

    @Qualifier("postLikeService")
    private final PostLikeService postLikeService;

    @Override
    @LoginRequired
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> createLike(
            @PathVariable Long postId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        postLikeService.createLike(session.getId(), postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @LoginRequired
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long postId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        postLikeService.deleteLike(session.getId(), postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
