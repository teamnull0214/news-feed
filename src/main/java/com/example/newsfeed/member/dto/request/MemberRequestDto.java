package com.example.newsfeed.member.dto.request;

import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "이름(실명)을 입력해주세요.")
    @Size(min = 1, max = 14, message = "이름은 1 ~ 14자 이어야 합니다.")
    private final String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 14, message = "닉네임은 1 ~ 14자 이어야 합니다.")
    private final String nickname;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email (message = "이메일 형식이 맞지 않습니다.")
    private final String email;

    @NotBlank (message = "비밀번호를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상 20자 이하, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#)가 포함되어야 합니다."
    )
    private final String password;

    @NotBlank (message = "설정한 비밀번호를 재입력해주세요.")
    private final String passwordCheck;

    public Member toEntity() {
        return new Member(name, nickname, email, PasswordEncoder.encode(password));
    }

}
