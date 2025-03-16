package com.barointern.java.presentation.controller;

import com.barointern.java.application.response.SignupResDto;
import com.barointern.java.application.service.AuthService;
import com.barointern.java.presentation.request.SignupReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthService authService;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<SignupResDto> signup(@RequestBody @Valid SignupReqDto signupReqDto) {
    SignupResDto response = authService.signup(signupReqDto);
    return ResponseEntity.ok(response);
  }

}
