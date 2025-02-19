package com.example.newsfeed.member.dto.response;

import com.example.newsfeed.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/* 다건 유저 출력 */
@Getter
@AllArgsConstructor
public class MemberListGetResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private int followerCount;

    public MemberListGetResponseDto(Member member, int followerCount) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.followerCount = followerCount;
    }

    public static MemberListGetResponseDto toDto(Member member, int followerCount){
        return new MemberListGetResponseDto(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                followerCount
        );
    }
}
