package com.example.newsfeed.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationFailedResponseDto <T> implements ApiResponseDto<T> {

    private T message;

    @Override
    public void validationFalied(T message) {
        this.message = message;
    }
}
