package com.barointern.yujin.presentation.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 username입니다."),
  LOGIN_FAIL(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다."),
  INVALID_SECRET_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "SECRET_KEY 초기화에 실패했습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 사용자가 존재하지 않습니다."),
  USERNAME_EXTRACTION_FAILED(HttpStatus.UNAUTHORIZED, "username 추출에 실패했습니다."),
  ROLE_EXTRACTION_FAILED(HttpStatus.UNAUTHORIZED, "역할 추출에 실패했습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
  UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰입니다."),
  JWT_VALIDATION_FAILED(HttpStatus.UNAUTHORIZED, "JWT 검증에 실패했습니다.")
  ;


  private final HttpStatus httpStatus;
  private final String message;
}