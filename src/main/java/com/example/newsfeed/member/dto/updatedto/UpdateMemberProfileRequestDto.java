package com.example.newsfeed.member.dto.updatedto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateMemberProfileRequestDto {

    @NotBlank(message = "이름(실명)을 입력해주세요.")
    @Size(min = 1, max = 14, message = "이름은 1 ~ 14자 이어야 합니다.")
    private final String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 14, message = "닉네임은 1 ~ 14자 이어야 합니다.")
    private final String nickname;

    private final String info;
    private final String mbti;

    public UpdateMemberProfileRequestDto(String name, String nickname, String info, String mbti) {
        this.name = name;
        this.nickname = nickname;
        this.info = info;
        this.mbti = mbti;
    }
}
