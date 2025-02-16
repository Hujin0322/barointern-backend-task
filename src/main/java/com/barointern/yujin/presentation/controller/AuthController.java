package com.barointern.yujin.presentation.controller;

import com.barointern.yujin.application.service.AuthService;
import com.barointern.yujin.presentation.request.SignInRequest;
import com.barointern.yujin.presentation.request.SignUpRequest;
import com.barointern.yujin.presentation.response.SignInResponse;
import com.barointern.yujin.presentation.response.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
@Tag(name = "Auth")
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthService authService;

  // 회원가입
  @PostMapping("/signup")
  @Operation(summary = "회원가입")
  public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authService.signUp(signUpRequest);
  }

  // 로그인
  @PostMapping("/signin")
  @Operation(summary = "로그인")
  public SignInResponse signIn(@Valid @RequestBody SignInRequest signInRequest,
      HttpServletResponse response) {
    String accessToken = authService.signIn(signInRequest.username(), signInRequest.password(), response);
    return new SignInResponse(accessToken);
  }

}
