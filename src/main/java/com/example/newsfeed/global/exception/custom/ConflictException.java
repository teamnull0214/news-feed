package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;

public class ConflictException {
    public static class MemberAlreadyExistsException extends CustomException {
        public MemberAlreadyExistsException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class AlreadyFollowing extends CustomException {
        public AlreadyFollowing(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
