package com.example.newsfeed.member.dto.findmemberdto;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class FindMemberDto {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbit;
    private final LocalDateTime createdAt;


    public FindMemberDto(Long id, String nickname, String email, String info, String mbit, LocalDateTime createdAt){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.info = info;
        this.mbit = mbit;
        this.createdAt = createdAt;
    }

}
