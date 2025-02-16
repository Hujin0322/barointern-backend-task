package com.barointern.yujin.presentation.controller;

import com.barointern.yujin.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthService authService;

  /*
  TODO: 회원가입 API 생성
  @PostMapping("/signup")
   */


  /*
  TODO: 로그인 API 생성
  @PostMapping("/signin")
   */

}
