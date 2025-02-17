package com.example.newsfeed.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import static com.example.newsfeed.global.exception.ApiResponseDto.validationFalied;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    /*valid 검증에 실패할 경우*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationFailed(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> validFailedList = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(validationFalied(exception.getStatusCode().value(), validFailedList));
    }
}
