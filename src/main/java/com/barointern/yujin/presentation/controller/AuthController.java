package com.barointern.yujin.presentation.controller;

import com.barointern.yujin.application.service.AuthService;
import com.barointern.yujin.presentation.request.SignUpRequest;
import com.barointern.yujin.presentation.response.SignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthService authService;

  // 회원가입
  @PostMapping("/signup")
  public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authService.signUp(signUpRequest);
  }

  /*
  TODO: 로그인 API 생성
  @PostMapping("/signin")
   */

}
