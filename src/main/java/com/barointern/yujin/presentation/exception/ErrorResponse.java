package com.barointern.yujin.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private final int status;
  private final String message;

  public static ErrorResponse of(HttpStatus httpStatus, String message) {
    return new ErrorResponse(httpStatus.value(), message);
  }

}
