package com.snowrepo.gateway.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Security Tests")
class SecurityTest {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private ApiKeyManager apiKeyManager;

  @Test
  @DisplayName("Should generate and validate JWT token")
  void testJwtToken() {
    UUID userId = UUID.randomUUID();
    String token = jwtTokenProvider.generateToken(userId);
    
    assertNotNull(token);
    assertTrue(jwtTokenProvider.validateToken(token));
    assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token));
  }

  @Test
  @DisplayName("Should validate API key")
  void testApiKey() {
    assertTrue(apiKeyManager.validateApiKey("test-api-key-123"));
    assertFalse(apiKeyManager.validateApiKey("invalid-key"));
  }

  @Test
  @DisplayName("Should get service name from API key")
  void testServiceName() {
    assertEquals("test-service", apiKeyManager.getServiceName("test-api-key-123"));
    assertNull(apiKeyManager.getServiceName("invalid-key"));
  }
}
