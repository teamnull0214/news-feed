package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException {

    public static class LoginRequiredException extends CustomException {
        public LoginRequiredException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
