package com.snowrepo.exception;

/**
 * Exception for conflict errors.
 * Thrown when a request conflicts with the current state of the resource.
 */
public class ConflictException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public ConflictException(String message, String errorCode) {
    super(message, errorCode, 409);
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public ConflictException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, 409, cause);
  }
}
