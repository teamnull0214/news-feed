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
    private int followerCount;

    public MemberListResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        // todo: 양방향을 사용할 수 있으므로 followerCount는 null
    }
}
