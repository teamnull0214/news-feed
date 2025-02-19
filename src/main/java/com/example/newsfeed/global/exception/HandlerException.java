package com.example.newsfeed.global.exception;

import io.swagger.v3.oas.annotations.Hidden;
import com.example.newsfeed.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import static com.example.newsfeed.global.exception.ApiResponseDto.validationFailed;

@Hidden
@Slf4j
@RestControllerAdvice
public class HandlerException {

    /*커스텀 예외 처리*/
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto<String>> handleCustomException(CustomException exception) {

        ApiResponseDtoImpl<String> reponse = new ApiResponseDtoImpl<>();
        reponse.fail(exception.getStatusCode(), exception.getMessage());

        return ResponseEntity.status(reponse.getStatusCode()).body(reponse);
    }

    /*valid 검증에 실패할 경우*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<List<String>>> handleValidationFailed(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<String> validFailedList = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        /*구현체 객체 생성 후 오버라이딩된 메서드를 통해 응답 필드에 오류 메세지 저장*/
        ApiResponseDtoImpl<List<String>> response = new ApiResponseDtoImpl<>();
        response.validationFailed(HttpStatus.BAD_REQUEST,validFailedList);

        /*상태 코드와 유효성 검증에 실패한 필드의 default message 응답*/
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /*RuntimeException 예외처리*/
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<String>> handleRuntimeException(RuntimeException exception) {
        ApiResponseDtoImpl<String> response = new ApiResponseDtoImpl<>();

        log.error("RuntimeException 발생: {}", exception.getMessage(), exception);

        response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
