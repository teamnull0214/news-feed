package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/* 상세한 타인 정보 */
@AllArgsConstructor
@Getter
public class MemberGetResponseDto {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String info;
    private final String mbit;
    private final LocalDateTime createdAt;

    public static MemberGetResponseDto toDto(Member member){
        return new MemberGetResponseDto(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getInfo(),
                member.getMbti(),
                member.getCreatedAt()
        );
    }

}
