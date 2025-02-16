package com.example.newsfeed.member.repository;

import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberById(Long id);
}
