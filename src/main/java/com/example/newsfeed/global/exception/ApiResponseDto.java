package com.example.newsfeed.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto <T>{

    private final Integer statusCode;

    private final List<String> message;

    private final T data;

    public static <T> ApiResponseDto <T> validationFalied(Integer statusCode, List<String> message) {
        return new ApiResponseDto<>(statusCode,message,null);
    }
}
