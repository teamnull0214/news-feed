package com.example.newsfeed.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDtoImpl<T> implements ApiResponseDto<T> {

    private Integer statusCode;

    private T message;

    private T data;

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
