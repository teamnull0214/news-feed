package com.example.newsfeed.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_MISMATCH(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    PASSWORD_CONFIRM_MISMATCH(HttpStatus.FORBIDDEN, "확인용 비밀번호가 일치하지 않습니다."),
    NEW_PASSWORD_SAME_AS_OLD(HttpStatus.FORBIDDEN, "새로운 비밀번호는 기존 비밀번호와 달라야 합니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."), // 추가
    MEMBER_DEACTIVATED(HttpStatus.FORBIDDEN, "탈퇴한 유저입니다."),
    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, "본인을 팔로우할 수 없습니다."), // 추가
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우한 사용자입니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인 해주세요");

    private final HttpStatus statusCode;
    private final String message;
}

