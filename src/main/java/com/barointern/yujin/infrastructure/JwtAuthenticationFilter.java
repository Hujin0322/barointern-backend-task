package com.barointern.yujin.infrastructure;

import com.barointern.yujin.presentation.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j(topic = "JWT 인증 필터")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = jwtUtil.extractToken(request);

    if (token != null) {
      try {
        jwtUtil.validateToken(token);

        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (AuthException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}