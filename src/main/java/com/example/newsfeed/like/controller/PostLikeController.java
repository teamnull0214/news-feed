package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController implements LikeController{

    private final LikeService likeService;

    @Override
    @LoginRequired
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> createLike(
            @PathVariable Long postId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        likeService.createLike(session.getId(), postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @LoginRequired
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long postId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        likeService.deleteLike(session.getId(), postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
