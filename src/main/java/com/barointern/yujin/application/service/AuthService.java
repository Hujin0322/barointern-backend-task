package com.barointern.yujin.application.service;

import com.barointern.yujin.domain.model.RoleType;
import com.barointern.yujin.domain.model.User;
import com.barointern.yujin.domain.repository.AuthRepository;
import com.barointern.yujin.presentation.exception.AuthException;
import com.barointern.yujin.presentation.exception.ErrorCode;
import com.barointern.yujin.presentation.request.SignUpRequest;
import com.barointern.yujin.presentation.response.SignUpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;

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


  /*
  TODO: 로그인 (SignIn)
   */

}
