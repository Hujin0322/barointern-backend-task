package com.barointern.yujin.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private final String ERROR_LOG = "[ERROR] %s %s";

  @ExceptionHandler(AuthException.class)
  protected ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
    log.error(String.format(ERROR_LOG, e.getHttpStatus(), e.getMessage()));

    ErrorResponse errorResponse = ErrorResponse.of(e.getHttpStatus(), e.getMessage());
    return new ResponseEntity<>(errorResponse, e.getHttpStatus());
  }

}
