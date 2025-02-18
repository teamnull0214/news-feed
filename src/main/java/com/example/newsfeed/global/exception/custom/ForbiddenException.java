package com.example.newsfeed.global.exception.custom;

import com.example.newsfeed.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ForbiddenException{

    /*비밀번호 불일치 예외*/
    public static class PasswordMismatchException extends CustomException {
        public PasswordMismatchException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*확인용 비밀번호 불일치 예외*/
    public static class PasswordConfirmMismatchException extends CustomException {
        public PasswordConfirmMismatchException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*변경할 비밀번호가 기존 비밀번호와 같은 경우 예외*/
    public static class NewPasswordSameAsOldException extends CustomException {
        public NewPasswordSameAsOldException(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    /*탈퇴한 유저 예외*/
    public static class MemberDeactivedException extends CustomException {
        public MemberDeactivedException(ErrorCode errorcode) {
            super(errorcode);
        }
    }

}
