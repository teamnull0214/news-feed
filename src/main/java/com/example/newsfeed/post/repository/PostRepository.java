package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


public interface PostRepository  extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.createdAt) BETWEEN :startDate AND :endDate " +
            "ORDER BY p.modifiedAt")
    List<Post> findAllByMemberIdAndModifiedAt(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.createdAt) BETWEEN :startDate AND :endDate " +
            "ORDER BY p.createdAt")
    List<Post> findAllByMemberIdAndCreatedAt(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 좋아요 기준으로 전체 조회 + 기간 필터링
    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.createdAt) BETWEEN :startDate AND :endDate " +
            "ORDER BY SIZE(p.postLikeList) DESC")
    List<Post> findAllByMemberIdAndLike(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /*유저 아이디가 포함되지 않은 게시물 전체 조회*/
    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.createdAt) BETWEEN :startDate AND :endDate ")
    List<Post> findAllByModifiedAt(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
