package com.example.newsfeed.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDtoImpl<T> implements ApiResponseDto<T> {

    private Integer statusCode;

    private T message;

    private T data;

    @Override
    public void ok(T data) {
        this.statusCode = HttpStatus.OK.value();
        this.data = data;
    }

    @Override
    public void validationFailed(HttpStatus statusCode, T message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }

    @Override
    public void fail(HttpStatus statusCode,T message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }
}
