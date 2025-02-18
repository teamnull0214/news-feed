package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import org.springframework.http.ResponseEntity;

public interface LikeController {

    ResponseEntity<Void> createLike(Long id, SessionMemberDto session);

    ResponseEntity<Void> deleteLike(Long id, SessionMemberDto session);
}
