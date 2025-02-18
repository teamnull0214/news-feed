package com.example.newsfeed.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto<T> implements ApiResponseDto<T> {

    private Integer statusCode;

    private T message;

    private T data;

    @Override
    public void ok(HttpStatus statusCode, T data) {

    }

    @Override
    public void validationFalied(HttpStatus statusCode, T message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }

    @Override
    public void fail(HttpStatus statusCode,T message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }
}
