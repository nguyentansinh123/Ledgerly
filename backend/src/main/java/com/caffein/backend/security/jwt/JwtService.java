package com.caffein.backend.security.jwt;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    Map<String, Object> claims = Map.of(TOKEN_TYPE, "ACCESS_TOKEN");
    return generateToken(username, claims, accessTokenExpiration);
  }

  public String generateRefreshToken(String username) {
    Map<String, Object> claims = Map.of(TOKEN_TYPE, "REFRESH_TOKEN");
    return generateToken(username, claims, refreshTokenExpiration);
  }

  private String generateToken(String username, Map<String, Object> claims, Long expiredTime) {
    return Jwts.builder()
        .claims(claims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() * expiredTime))
        .signWith(key)
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaims(token).getSubject();
  }

  private Claims extractClaims(String token) {
    try {
      return Jwts
          .parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (Exception e) {
      throw new RuntimeException("Invalid Jwt Token ", e);
    }

  }

  public boolean isTokenValid(String token, String expectedUsername) {
    String userName = extractUsername(token);
    return userName.equals(expectedUsername) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  public String refreshAccessToken(String refreshToken) {

    Claims claims = extractClaims(refreshToken);
    if (!"REFRESH_TOKEN".equals(claims.get(TOKEN_TYPE))) {
      throw new RuntimeException("Invalid token type");
    }

    if (isTokenExpired(refreshToken)) {
      throw new RuntimeException("refresh token expired");
    }

    String username = claims.getSubject();
    return generateAccessToken(username);

  }

}
