package com.barointern.java.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Auth 관련 에러 처리
  @ExceptionHandler(AuthException.class)
  public ResponseEntity<Object> handleAuthException(AuthException e) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", Map.of(
        "code", e.getCode(),
        "message", e.getMessage()));

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  // 유효성 검사
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    Map<String, Object> response = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      response.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
