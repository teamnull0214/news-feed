package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface PostRepository  extends JpaRepository<Post, Long> {

    /*
    feat/post-read 브랜치
    Java 8 이상부터 사용가능한 코드: default method 사용
    postId를 통해 특정 게시물을 찾는(조회하는) 서비스
    찾기 실패시 status 404 Not Found throw
    */
    default Post findByIdOrElseThrow(Long postId) {
        return findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, postId + "의 postId값을 갖는 게시물이 존재하지 않습니다"));
    }

}
