package com.barointern.yujin.infrastructure.jwt;

import com.barointern.yujin.infrastructure.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j(topic = "사용자 인증 필터")
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String email = request.getHeader("X-USER-EMAIL");
    String role = request.getHeader("X-USER-ROLE");

    if (email != null && role != null) {
      try {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        log.info("auth {}", authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        log.error("사용자 인증 실패: {}", e.getMessage());
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}