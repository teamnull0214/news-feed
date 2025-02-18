package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException{

    public static class CannotFollowSelfException extends CustomException {
        public CannotFollowSelfException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
