package com.example.newsfeed.member.repository;

import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    // 탈퇴하지 않은 등록된 유저 중 email 값으로 조회 (로그인에 사용)
    @Query("SELECT u FROM Member u WHERE u.email = :email AND u.isDeleted = false")
    Optional<Member> findActiveMemberByEmail(@Param("email") String email);

    // 탈퇴하지 않은 등록된 유저 중 id 값으로 조회 (타인 member-read 에 사용)
    @Query("SELECT u FROM Member u WHERE u.id = :id AND u.isDeleted = false")
    Optional<Member> findActiveMemberById(@Param("id") Long id);

    // 탈퇴하지 않은 유저 조회 (다건 member-read 에 사용)
    @Query("SELECT u FROM Member u WHERE u.isDeleted = false")
    List<Member> findActiveMemberAll();
}
