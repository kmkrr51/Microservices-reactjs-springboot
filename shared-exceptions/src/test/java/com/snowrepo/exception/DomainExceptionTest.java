package com.snowrepo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DomainException Tests")
class DomainExceptionTest {

  @Test
  @DisplayName("Should create domain exception with 400 status code")
  void testCreateDomainException() {
    DomainException exception = new DomainException("Business rule violated", "DOMAIN_ERROR");

    assertEquals("Business rule violated", exception.getMessage());
    assertEquals("DOMAIN_ERROR", exception.getErrorCode());
    assertEquals(400, exception.getStatusCode());
  }

  @Test
  @DisplayName("Should create domain exception with cause")
  void testCreateDomainExceptionWithCause() {
    Throwable cause = new RuntimeException("Root cause");
    DomainException exception = new DomainException("Business rule violated", "DOMAIN_ERROR",
        cause);

    assertEquals("Business rule violated", exception.getMessage());
    assertEquals("DOMAIN_ERROR", exception.getErrorCode());
    assertEquals(400, exception.getStatusCode());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should be instance of ApplicationException")
  void testIsApplicationException() {
    DomainException exception = new DomainException("Business rule violated", "DOMAIN_ERROR");

    assertInstanceOf(ApplicationException.class, exception);
  }
}
