package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class deleteRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

}
