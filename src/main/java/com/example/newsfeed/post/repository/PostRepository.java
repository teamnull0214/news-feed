package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "AND FUNCTION('DATE', p.createdAt) BETWEEN :startDate AND :endDate ")
    Page<Post> findAllByMemberId(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    // 좋아요 기준으로 전체 조회 + 기간 필터링
    @Query("SELECT p FROM Post p " +
            "WHERE p.member.id = :memberId " +
            "ORDER BY SIZE(p.postLikeList) DESC")
    Page<Post> findAllByMemberIdAndLike(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    /*유저 아이디가 포함되지 않은 게시물 전체 조회*/
    @Query("SELECT p FROM Post p " +
            "WHERE FUNCTION('DATE', p.modifiedAt) BETWEEN :startDate AND :endDate ")
    Page<Post> findAllByModifiedAt(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
