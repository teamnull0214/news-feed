package com.example.newsfeed.global.entity;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMemberDto {

    private Long id;
    private String username;
    private String nickname;
    private String email;

    public SessionMemberDto setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Member toEntity() {
        return new Member(id, username, nickname, email);
    }
}
