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
}
