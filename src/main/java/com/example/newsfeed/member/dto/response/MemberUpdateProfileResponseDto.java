package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberUpdateProfileResponseDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbti;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static MemberUpdateProfileResponseDto toDto(Member member){
        return new MemberUpdateProfileResponseDto(
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
