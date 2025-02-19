package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/* 회원가입 시 출력 */
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdAt;

    public static MemberResponseDto toDto(Member member){
        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getCreatedAt()
        );
    }
}
