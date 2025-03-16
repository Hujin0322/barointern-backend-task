package com.barointern.java.application.service;

import com.barointern.java.application.response.SignupResDto;
import com.barointern.java.domain.model.RoleType;
import com.barointern.java.domain.model.User;
import com.barointern.java.domain.repository.UserRepository;
import com.barointern.java.infrastructure.exception.AuthException;
import com.barointern.java.presentation.request.SignupReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원 가입
  @Transactional
  public SignupResDto signup(SignupReqDto signupReqDto) {
    String username = signupReqDto.username();

    // 중복 확인
    if (userRepository.findByUsername(username).isPresent()) {
      throw new AuthException("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.");
    }
    String password = passwordEncoder.encode(signupReqDto.password());
    String nickname = signupReqDto.nickname();

    User user = User.create(username, password, nickname, RoleType.USER);
    userRepository.save(user);

    return SignupResDto.from(user);
  }

}
