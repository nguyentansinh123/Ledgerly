package com.caffein.backend.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

  private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
  private static final String TOKEN_TYPE = "token_type";

  @Value("${app.security.jwt.access-token-expiration}")
  private Long accessTokenExpiration;

  @Value("${app.security.jwt.refresh-token-expiration}")
  private Long refreshTokenExpiration;

  public String generateAccessToken(String username) {
    return "";
  }

  public String generateRefreshToken(String username) {
    return "";
  }

  public boolean isTokenValid(String token) {
    return false;
  }

  private boolean isTokenExpired(String token) {
    return false;
  }

  public String refreshAccessToken(String refreshToken) {
    return "";
  }

}
