package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


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

   /* @Query("SELECT p FROM Post p WHERE p.member.id = :memberId ORDER BY p.modifiedAt")
    List<Post> findAllByModifiedAt(
            @Param("memberId") Long memberId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query("SELECT p FROM Post p WHERE p.member.id = :memberId ORDER BY p.createdAt")
    List<Post> findAllByCreatedAt(
            @Param("memberId") Long memberId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );*/


    // 좋아요 기준으로 전체 조회 + 기간 필터링
    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.modifiedAt) BETWEEN :startDate AND :endDate " +
            "ORDER BY SIZE(p.postLikeList) DESC")
    List<Post> findAllByLike(
            @Param("memberId") Long memberId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

}
