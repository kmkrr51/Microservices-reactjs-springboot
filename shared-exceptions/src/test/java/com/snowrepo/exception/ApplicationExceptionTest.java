package com.snowrepo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApplicationException Tests")
class ApplicationExceptionTest {

  @Test
  @DisplayName("Should create exception with message and error code")
  void testCreateWithMessageAndErrorCode() {
    ApplicationException exception = new ApplicationException("Test message", "TEST_ERROR");

    assertEquals("Test message", exception.getMessage());
    assertEquals("TEST_ERROR", exception.getErrorCode());
    assertEquals(500, exception.getStatusCode());
  }

  @Test
  @DisplayName("Should create exception with message, error code, and status code")
  void testCreateWithMessageErrorCodeAndStatusCode() {
    ApplicationException exception = new ApplicationException("Test message", "TEST_ERROR", 400);

    assertEquals("Test message", exception.getMessage());
    assertEquals("TEST_ERROR", exception.getErrorCode());
    assertEquals(400, exception.getStatusCode());
  }

  @Test
  @DisplayName("Should create exception with message, error code, and cause")
  void testCreateWithMessageErrorCodeAndCause() {
    Throwable cause = new RuntimeException("Root cause");
    ApplicationException exception = new ApplicationException("Test message", "TEST_ERROR", cause);

    assertEquals("Test message", exception.getMessage());
    assertEquals("TEST_ERROR", exception.getErrorCode());
    assertEquals(500, exception.getStatusCode());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should create exception with all parameters")
  void testCreateWithAllParameters() {
    Throwable cause = new RuntimeException("Root cause");
    ApplicationException exception = new ApplicationException("Test message", "TEST_ERROR", 400,
        cause);

    assertEquals("Test message", exception.getMessage());
    assertEquals("TEST_ERROR", exception.getErrorCode());
    assertEquals(400, exception.getStatusCode());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should be instance of RuntimeException")
  void testIsRuntimeException() {
    ApplicationException exception = new ApplicationException("Test message", "TEST_ERROR");

    assertInstanceOf(RuntimeException.class, exception);
  }
}
