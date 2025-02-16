package com.barointern.yujin.infrastructure;

import com.barointern.yujin.presentation.exception.AuthException;
import com.barointern.yujin.presentation.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Jwt 토큰 생성")
@Component
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String JWT_ROLE_KEY = "role";  // JWT 내 권한 키
  public static final String BEARER_PREFIX = "Bearer "; // Token 식별자
  public static final String REFRESH_TOKEN_COOKIE = "RefreshToken";  // Refresh Token 쿠키 이름

  @Value("${service.jwt.access-token-expiration}")
  private long accessTokenExpiration;

  @Value("${service.jwt.refresh-token-expiration}")
  private long refreshTokenExpiration;

  @Value("${service.jwt.secret-key}")
  private String secretKey; // Base64 인코딩된 비밀키
  private SecretKey key; // 디코딩된 비밀키 객체

  @PostConstruct
  public void init() {
    try {
      byte[] decodedKey = Base64.getDecoder().decode(secretKey);
      key = Keys.hmacShaKeyFor(decodedKey);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(ErrorCode.INVALID_SECRET_KEY.getMessage(), e);
    }
  }

  // AccessToken 생성
  public String createAccessToken(String username, String role) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + accessTokenExpiration);

    return BEARER_PREFIX + Jwts.builder()
        .subject(username)
        .claim(JWT_ROLE_KEY, role)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(key)
        .compact();
  }

  // RefreshToken 생성
  public String createRefreshToken(String username) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + refreshTokenExpiration);

    return Jwts.builder()
        .subject(username)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(key)
        .compact();
  }

  // 토큰 검증
  public void validateToken(String token) {
    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .verifyWith(key)
          .build().parseSignedClaims(token);
      log.info("payload: {}", claimsJws.getPayload().toString());
    } catch (ExpiredJwtException e) {
      log.error("만료된 JWT token 입니다. Token: {}", token);
      throw new AuthException(ErrorCode.EXPIRED_TOKEN);
    } catch (UnsupportedJwtException e) {
      log.error("지원되지 않는 JWT 토큰 입니다. Token: {}", token);
      throw new AuthException(ErrorCode.UNSUPPORTED_TOKEN);
    } catch (MalformedJwtException | IllegalArgumentException e) {
      log.error("잘못된 JWT 토큰 입니다. Token: {}", token);
      throw new AuthException(ErrorCode.INVALID_TOKEN);
    } catch (SecurityException | JwtException e) {
      log.error("JWT 검증에 실패했습니다. Token: {}", token);
      throw new AuthException(ErrorCode.JWT_VALIDATION_FAILED);  // 예외 던지기
    }
  }

  // 헤더에서 JWT 추출
  public String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length()).trim();
    }
    return null;
  }

  // Claims 추출
  private Claims parseClaims(String token) {
    Jws<Claims> jws = Jwts.parser()
        .verifyWith(key)
        .build().parseSignedClaims(token);
    return jws.getPayload();
  }

  // username 추출
  public String extractUsername(String token) {
    try {
      Claims claims = parseClaims(token);
      return claims.getSubject();
    } catch (JwtException e) {
      log.error("이메일 추출에 실패했습니다. Token: {}", token, e);
      throw new AuthException(ErrorCode.USERNAME_EXTRACTION_FAILED);
    }
  }

  // 역할 추출
  public String extractRole(String token) {
    try {
      Claims claims = parseClaims(token);
      return claims.get(JWT_ROLE_KEY, String.class);
    } catch (JwtException e) {
      log.error("역할 추출에 실패했습니다. Token: {}", token, e);
      throw new AuthException(ErrorCode.ROLE_EXTRACTION_FAILED);
    }
  }

  // RefreshToken 쿠키에 저장
  public void addRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {
    log.info("쿠키 설정 값: {}", refreshToken);
    Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(86400);
    response.addCookie(cookie);
    log.info("Refresh token 쿠키가 성공적으로 설정되었습니다. 토큰: {}", refreshToken);
  }
}