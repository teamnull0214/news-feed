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

    @Size(min = 1, max = 200, message = "한마디는 200자 이하여야 합니다.")
    private String info;

    @Size(min = 4, max = 4, message = "MBTI는 4자리여야 합니다.")
    private String mbti;
}
