package com.example.newsfeed.member.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateProfileRequestDto {

    @Size(min = 1, max = 14, message = "이름은 1 ~ 14자 이어야 합니다.")
    private String username;

    @Size(min = 1, max = 14, message = "닉네임은 1 ~ 14자 이어야 합니다.")
    private String nickname;

    private String info;
    private String mbti;
}
