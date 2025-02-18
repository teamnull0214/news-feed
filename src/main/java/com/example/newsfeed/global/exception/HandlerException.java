package com.example.newsfeed.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class HandlerException {

    /*valid 검증에 실패할 경우*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationFailedResponseDto<Object>> handleValidationFailed(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> validFailedList = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        /*구현체 객체 생성 후 오버라이딩된 메서드를 통해 응답 필드에 오류 메세지 저장*/
        ValidationFailedResponseDto<Object> validationFailedResponseDto = new ValidationFailedResponseDto<>();
        validationFailedResponseDto.validationFalied(validFailedList);

        /*상태 코드와 유효성 검증에 실패한 필드의 default message 응답*/
        return ResponseEntity.status(exception.getStatusCode()).body(validationFailedResponseDto);
    }
}
