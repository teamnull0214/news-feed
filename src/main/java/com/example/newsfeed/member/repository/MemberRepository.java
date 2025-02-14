package com.example.newsfeed.member.repository;

import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member, Long> {
}
