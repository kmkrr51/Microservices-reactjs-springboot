package com.snowrepo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Status Codes Tests")
class ExceptionStatusCodesTest {

  @Test
  @DisplayName("ValidationException should have 400 status code")
  void testValidationExceptionStatusCode() {
    ValidationException exception = new ValidationException("Invalid input", "VALIDATION_ERROR");

    assertEquals(400, exception.getStatusCode());
  }

  @Test
  @DisplayName("NotFoundException should have 404 status code")
  void testNotFoundExceptionStatusCode() {
    NotFoundException exception = new NotFoundException("Resource not found", "NOT_FOUND");

    assertEquals(404, exception.getStatusCode());
  }

  @Test
  @DisplayName("ConflictException should have 409 status code")
  void testConflictExceptionStatusCode() {
    ConflictException exception = new ConflictException("Resource conflict", "CONFLICT");

    assertEquals(409, exception.getStatusCode());
  }

  @Test
  @DisplayName("InternalServerException should have 500 status code")
  void testInternalServerExceptionStatusCode() {
    InternalServerException exception = new InternalServerException("Server error", "SERVER_ERROR");

    assertEquals(500, exception.getStatusCode());
  }

  @Test
  @DisplayName("All exceptions should have error codes")
  void testAllExceptionsHaveErrorCodes() {
    ValidationException ve = new ValidationException("Invalid", "VALIDATION_ERROR");
    NotFoundException nfe = new NotFoundException("Not found", "NOT_FOUND");
    ConflictException ce = new ConflictException("Conflict", "CONFLICT");
    InternalServerException ise = new InternalServerException("Error", "SERVER_ERROR");

    assertNotNull(ve.getErrorCode());
    assertNotNull(nfe.getErrorCode());
    assertNotNull(ce.getErrorCode());
    assertNotNull(ise.getErrorCode());
  }

  @Test
  @DisplayName("All exceptions should be ApplicationException instances")
  void testAllExceptionsAreApplicationExceptions() {
    ValidationException ve = new ValidationException("Invalid", "VALIDATION_ERROR");
    NotFoundException nfe = new NotFoundException("Not found", "NOT_FOUND");
    ConflictException ce = new ConflictException("Conflict", "CONFLICT");
    InternalServerException ise = new InternalServerException("Error", "SERVER_ERROR");

    assertInstanceOf(ApplicationException.class, ve);
    assertInstanceOf(ApplicationException.class, nfe);
    assertInstanceOf(ApplicationException.class, ce);
    assertInstanceOf(ApplicationException.class, ise);
  }
}
