package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentLikeController{

    private final CommentLikeService commentLikeService;

    @LoginRequired
    @PostMapping("/{commentId}/likes")
    public ResponseEntity<Void> createLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        commentLikeService.createLike(session.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        commentLikeService.deleteLike(session.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
