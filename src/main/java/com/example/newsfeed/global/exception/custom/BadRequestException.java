package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException{

    public static class CannotFollowSelfException extends CustomException {
        public CannotFollowSelfException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*이미 언팔로우를 한 경우*/
    public static class AlreadyUnFollowedException extends CustomException {
        public AlreadyUnFollowedException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
