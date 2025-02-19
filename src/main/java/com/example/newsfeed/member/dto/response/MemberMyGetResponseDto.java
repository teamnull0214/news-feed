package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/* 상세한 본인 유저 정보(+update) */
@Getter
@AllArgsConstructor
public class MemberMyGetResponseDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbti;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static MemberMyGetResponseDto toDto(Member member){
        return new MemberMyGetResponseDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getInfo(),
                member.getMbti(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}
