package com.example.newsfeed.global.exception;

import com.example.newsfeed.global.exception.custom.CustomException;
import com.example.newsfeed.global.exception.custom.ForbiddenException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@Builder
public class HandlerException {

    /*커스텀 예외 처리*/
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto<Object>> handleCustomException(CustomException exception) {

        ErrorResponseDto<Object> objectErrorResponseDto = new ErrorResponseDto<>();
        objectErrorResponseDto.fail(exception.getStatusCode(), exception.getMessage());

        return ResponseEntity.status(exception.getStatusCode()).body(objectErrorResponseDto);
    }

    /*valid 검증에 실패할 경우*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto<Object>> handleValidationFailed(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> validFailedList = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        /*구현체 객체 생성 후 오버라이딩된 메서드를 통해 응답 필드에 오류 메세지 저장*/
        ErrorResponseDto<Object> errorResponseDto = new ErrorResponseDto<>();
        errorResponseDto.validationFalied(HttpStatus.BAD_REQUEST,validFailedList);

        /*상태 코드와 유효성 검증에 실패한 필드의 default message 응답*/
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponseDto);
    }

    /*todo: 런타입 에러도 추가하기*/
}
