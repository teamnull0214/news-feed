package com.example.newsfeed.member.dto;

import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemberListResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private long followerCount;

    public MemberListResponseDto(Member member, long followerCount) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.followerCount = followerCount;
    }
}
