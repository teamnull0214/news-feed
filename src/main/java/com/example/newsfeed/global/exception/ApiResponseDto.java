package com.example.newsfeed.global.exception;

import org.springframework.http.HttpStatus;

public interface ApiResponseDto<T> {

    /*성공 했을 때 응답*/
    void ok(HttpStatus statusCode, T data);

    /*유효성 검사에 대한 예외 응답*/
    void validationFalied(HttpStatus statusCode, T message);

    /*커스텀 예외 처리에 대한 응답*/
    void fail(HttpStatus statusCode, T message);

}