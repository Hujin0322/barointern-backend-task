package com.barointern.yujin.presentation.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private final String ERROR_LOG = "[ERROR] %s %s";

  // AuthException
  @ExceptionHandler(AuthException.class)
  protected ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
    log.error(String.format(ERROR_LOG, e.getHttpStatus(), e.getMessage()));

    ErrorResponse errorResponse = ErrorResponse.of(e.getHttpStatus(), e.getMessage());
    return new ResponseEntity<>(errorResponse, e.getHttpStatus());
  }

  // 유효성 검사
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException e) {
    log.error(String.format(ERROR_LOG, HttpStatus.BAD_REQUEST, e.getMessage()));

    List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "유효성 검사 실패",
        errorMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
