package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
