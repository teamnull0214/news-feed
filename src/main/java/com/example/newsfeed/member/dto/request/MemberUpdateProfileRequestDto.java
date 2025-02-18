package com.example.newsfeed.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateProfileRequestDto {

    @NotBlank(message = "이름(실명)을 입력해주세요.")
    @Size(min = 1, max = 14, message = "이름은 1 ~ 14자 이어야 합니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 14, message = "닉네임은 1 ~ 14자 이어야 합니다.")
    private String nickname;

    private String info;
    private String mbti;
}
