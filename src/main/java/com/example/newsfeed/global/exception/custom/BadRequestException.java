package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException{

    public static class CannotFollowSelfException extends CustomException {
        public CannotFollowSelfException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*본인의 정보에 좋아요를 하는 경우*/
    public static class CannotLikeOwnEntityException extends CustomException {
        public CannotLikeOwnEntityException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*잘못된 URL 또는 METHOD가 입력된 경우*/
    public static class InvalidUrlOrMethodException extends CustomException {
        public InvalidUrlOrMethodException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
