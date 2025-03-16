package com.barointern.java.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {

  private final String code;
  private final String message;

  public AuthException(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
