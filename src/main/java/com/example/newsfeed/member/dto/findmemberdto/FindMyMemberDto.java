package com.example.newsfeed.member.dto.findmemberdto;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class FindMyMemberDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbit;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public FindMyMemberDto(Long id, String username, String nickname, String email, String info, String mbit, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.info = info;
        this.mbit = mbit;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
