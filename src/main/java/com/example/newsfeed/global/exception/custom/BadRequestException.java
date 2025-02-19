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

    /*이미 좋아요를 누른 경우*/
    public static class AlreadyLikedException extends CustomException {
        public AlreadyLikedException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*이미 좋아요를 해제한 경우*/
    public static class AlreadyUnLikedException extends CustomException {
        public AlreadyUnLikedException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*본인의 정보에 좋아요를 하는 경우*/
    public static class CannotLikeOwnEntityException extends CustomException {
        public CannotLikeOwnEntityException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
