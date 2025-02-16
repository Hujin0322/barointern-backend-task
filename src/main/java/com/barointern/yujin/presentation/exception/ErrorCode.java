package com.barointern.yujin.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 username입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}