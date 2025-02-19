package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.like.service.CommentLikeService;
import com.example.newsfeed.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentLikeController implements LikeController{

    @Autowired
    @Qualifier("commentLikeService")
    private final CommentLikeService commentLikeService;

    @Override
    @PostMapping("/{commentId}/likes")
    public ResponseEntity<Void> createLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        commentLikeService.createLike(session.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = "member") SessionMemberDto session
    ) {
        commentLikeService.deleteLike(session.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
