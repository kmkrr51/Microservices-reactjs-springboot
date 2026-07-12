package com.snowrepo.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoggingUtils Tests")
class LoggingUtilsTest {

  @BeforeEach
  void setUp() {
    LoggingUtils.clearMDC();
  }

  @Test
  @DisplayName("Should set and get correlation ID")
  void testCorrelationId() {
    UUID id = UUID.randomUUID();
    LoggingUtils.setCorrelationId(id);
    assertEquals(id.toString(), LoggingUtils.getCorrelationId());
  }

  @Test
  @DisplayName("Should set and get user ID")
  void testUserId() {
    UUID id = UUID.randomUUID();
    LoggingUtils.setUserId(id);
    assertEquals(id.toString(), LoggingUtils.getUserId());
  }

  @Test
  @DisplayName("Should set and get request ID")
  void testRequestId() {
    UUID id = UUID.randomUUID();
    LoggingUtils.setRequestId(id);
    assertEquals(id.toString(), LoggingUtils.getRequestId());
  }

  @Test
  @DisplayName("Should clear MDC")
  void testClearMDC() {
    LoggingUtils.setCorrelationId(UUID.randomUUID());
    LoggingUtils.clearMDC();
    assertNull(LoggingUtils.getCorrelationId());
  }

  @Test
  @DisplayName("Should handle null values")
  void testNullValues() {
    LoggingUtils.setCorrelationId(null);
    assertNull(LoggingUtils.getCorrelationId());
  }
}
