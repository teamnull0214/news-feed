package com.example.newsfeed.global.exception;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface ApiResponseDto<T> {

    /*유효성 검사에 대한 예외 응답*/
    void validationFalied(T message);

}