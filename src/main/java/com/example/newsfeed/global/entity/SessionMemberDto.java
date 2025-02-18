package com.example.newsfeed.global.entity;

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
}
