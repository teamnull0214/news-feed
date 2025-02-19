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
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."), // 추가
    MEMBER_DEACTIVATED(HttpStatus.FORBIDDEN, "탈퇴한 유저입니다."),
    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, "본인을 팔로우할 수 없습니다."), // 추가
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우한 사용자입니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인 해주세요"),
    CANNOT_UPDATE_OTHERS_DATA(HttpStatus.FORBIDDEN, "다른 사람의 정보는 수정 및 삭제가 불가능합니다."),
    ALREADY_UNFOLLOWED(HttpStatus.BAD_REQUEST, "이미 언팔로우한 유저입니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 내용을 찾을 수 없습니다."),
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 상태입니다."),
    ALREADY_UNLIKED(HttpStatus.BAD_REQUEST, "이미 좋아요 해제를 한 댓글입니다."),
    CANNOT_LIKE_OWN_ENTITY(HttpStatus.BAD_REQUEST, "본인이 작성한 정보는 좋아요/좋아요 취소를 누를 수 없다."),
    LIKE_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 기록이 존재하지 않음.");

    private final HttpStatus statusCode;
    private final String message;
}

