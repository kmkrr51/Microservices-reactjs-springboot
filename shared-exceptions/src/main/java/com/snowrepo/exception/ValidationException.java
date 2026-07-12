package com.snowrepo.exception;

/**
 * Exception for validation errors.
 * Thrown when input validation fails.
 */
public class ValidationException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public ValidationException(String message, String errorCode) {
    super(message, errorCode, 400);
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public ValidationException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, 400, cause);
  }
}
