package com.barointern.java.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.barointern.java.application.response.SignupResDto;
import com.barointern.java.domain.model.RoleType;
import com.barointern.java.domain.model.User;
import com.barointern.java.domain.repository.UserRepository;
import com.barointern.java.infrastructure.exception.AuthException;
import com.barointern.java.presentation.request.SignupReqDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @InjectMocks
  private AuthService authService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  private SignupReqDto signupReqDto;

  @BeforeEach
  public void setUp() {
    signupReqDto = SignupReqDto.builder()
        .username("testuser")
        .password("1234abcd!!!")
        .nickname("Mentos")
        .build();
  }

  @Test
  @DisplayName("회원 가입 성공 테스트")
  public void testSignupSuccess() {
    String encodedPassword = signupReqDto.password();

    User user = User.create(
        signupReqDto.username(),
        encodedPassword,
        signupReqDto.nickname(),
        RoleType.USER
    );

    given(userRepository.findByUsername(signupReqDto.username())).willReturn(Optional.empty());
    given(passwordEncoder.encode(signupReqDto.password())).willReturn(encodedPassword);
    given(userRepository.save(any(User.class))).willReturn(user);

    //when: 회원 가입 서비스 호출
    SignupResDto response = authService.signup(signupReqDto);

    // Then: 응답에서 회원 가입한 사용자 정보가 정확한지 검증
    assertThat(response.username()).isEqualTo(signupReqDto.username());
    assertThat(response.nickname()).isEqualTo(signupReqDto.nickname());
  }

  @Test
  @DisplayName("회원 가입 실패 - 중복된 아이디")
  public void testDuplicateSignup() {
    User existingUser = User.create(
        signupReqDto.username(),
        "encodedPassword",
        signupReqDto.nickname(),
        RoleType.USER
    );

    given(userRepository.findByUsername(signupReqDto.username())).willReturn(
        Optional.of(existingUser));

    assertThatThrownBy(
        () -> authService.signup(signupReqDto))
        .isInstanceOf(AuthException.class)
        .hasMessage("이미 가입된 사용자입니다.");
  }
}