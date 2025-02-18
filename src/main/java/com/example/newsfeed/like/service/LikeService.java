package com.example.newsfeed.like.service;

public interface LikeService {

    void createLike(Long memberId, Long Id);

    void deleteLike(Long memberId, Long id);
}
