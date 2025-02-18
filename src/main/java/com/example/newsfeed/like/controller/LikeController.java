package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.entity.SessionMemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.function.EntityResponse;

public interface LikeController {

    ResponseEntity<Void> createLike(Long id, SessionMemberDto session);

    ResponseEntity<Void> deleteLike(Long id, SessionMemberDto session);
}
