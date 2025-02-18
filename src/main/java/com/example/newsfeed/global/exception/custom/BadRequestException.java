package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;

public class BadRequestException {
    public static class CannotFollowSelfException extends CustomException {
        public CannotFollowSelfException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
