package com.barointern.yujin.application.service;

import com.barointern.yujin.domain.model.RoleType;
import com.barointern.yujin.domain.model.User;
import com.barointern.yujin.domain.repository.AuthRepository;
import com.barointern.yujin.infrastructure.JwtUtil;
import com.barointern.yujin.presentation.exception.AuthException;
import com.barointern.yujin.presentation.exception.ErrorCode;
import com.barointern.yujin.presentation.request.SignUpRequest;
import com.barointern.yujin.presentation.response.SignUpResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  // 회원가입
  public SignUpResponse signUp(SignUpRequest signUpRequest) {
    if (authRepository.existsByUsername(signUpRequest.username())) {
      throw new AuthException(ErrorCode.DUPLICATE_USERNAME);
    }

    User user = User.builder()
        .username(signUpRequest.username())
        .password(passwordEncoder.encode(signUpRequest.password()))
        .nickname(signUpRequest.nickname())
        .role(RoleType.USER)
        .build();

    authRepository.save(user);

    return SignUpResponse.from(user.getUsername(), user.getNickname(), List.of(user.getRole()));
  }

  // 로그인
  public String signIn(String username, String password, HttpServletResponse response) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      User user = getUser(username);
      String role = user.getRole().getAuthorityName();

      String accessToken = jwtUtil.createAccessToken(username, role);
      String refreshToken = jwtUtil.createRefreshToken(username);

      jwtUtil.addRefreshTokenToCookie(refreshToken, response);

      return accessToken;
    } catch (AuthenticationException ex) {
      log.error("로그인 실패: {}", ex.getMessage());
      throw new AuthException(ErrorCode.LOGIN_FAIL);
    }
  }


  /*
  private method
   */

  // 사용자 확인
  private User getUser(String username) {
    return authRepository.findByUsername(username)
        .orElseThrow(
            () -> new AuthException(ErrorCode.USER_NOT_FOUND)); // Optional에서 값을 꺼내거나 예외를 던짐
  }

}
