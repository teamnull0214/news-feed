package com.example.newsfeed.member.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private final Long id;

    private final String name;

    private final String nickname;

    private final String email;

    private final LocalDateTime createdAt;


    public MemberResponseDto(Long id, String name, String nickname, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
    }
}
