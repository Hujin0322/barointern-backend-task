package com.barointern.yujin.infrastructure;

import com.barointern.yujin.domain.model.User;
import com.barointern.yujin.domain.repository.AuthRepository;
import com.barointern.yujin.presentation.exception.AuthException;
import com.barointern.yujin.presentation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = authRepository.findByUsername(username)
        .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}
