package com.snowrepo.exception;

/**
 * Exception for domain-level errors.
 * Thrown when a business rule is violated.
 */
public class DomainException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public DomainException(String message, String errorCode) {
    super(message, errorCode, 400);
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public DomainException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, 400, cause);
  }
}
