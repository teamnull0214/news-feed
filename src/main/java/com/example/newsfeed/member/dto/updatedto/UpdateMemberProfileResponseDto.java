package com.example.newsfeed.member.dto.updatedto;

import lombok.Getter;
import org.apache.naming.factory.SendMailFactory;

import java.time.LocalDateTime;

@Getter
public class UpdateMemberProfileResponseDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbti;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateMemberProfileResponseDto(Long id, String username, String nickname, String email, String info, String mbti, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.info = info;
        this.mbti = mbti;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
