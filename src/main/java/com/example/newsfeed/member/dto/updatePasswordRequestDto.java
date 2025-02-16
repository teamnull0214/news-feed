package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class updatePasswordRequestDto {

    @NotBlank (message = "비밀번호를 입력해주세요")
    private final String oldPassword;

    @NotBlank (message = "변경할 비밀번호를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상 20자 이하, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#)가 포함되어야 합니다."
    )
    private final String newPassword;

    @NotBlank (message = "비밀번호 확인을 입력해주세요")
    private final String newPasswordCheck;
}
