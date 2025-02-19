package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException {

    /*유저를 찾을 수 없는 경우 예외*/
    public static class MemberNotFoundException extends CustomException {
        public MemberNotFoundException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*게시물을 찾을 수 없는 경우 예외*/
    public static class PostNotFoundException extends CustomException {
        public PostNotFoundException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*팔로우 내용을 찾을 수 없는 경우*/
    public static class FollowNotFoundException extends CustomException {
        public FollowNotFoundException(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}
