package com.snowrepo.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT token provider for token generation and validation.
 */
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret:snowrepo-secret-key-for-jwt-token-generation-and-validation}")
  private String jwtSecret;

  @Value("${jwt.expiration:86400000}")
  private long jwtExpirationMs;

  /**
   * Generates JWT token for user.
   *
   * @param userId the user ID
   * @return the JWT token
   */
  public String generateToken(UUID userId) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * Validates JWT token.
   *
   * @param token the JWT token
   * @return true if valid, false otherwise
   */
  public boolean validateToken(String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets user ID from token.
   *
   * @param token the JWT token
   * @return the user ID
   */
  public UUID getUserIdFromToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody();
    return UUID.fromString(claims.getSubject());
  }
}
