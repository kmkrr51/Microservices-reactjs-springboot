package com.snowrepo.exception;

/**
 * Exception for resource not found errors.
 * Thrown when a requested resource cannot be found.
 */
public class NotFoundException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public NotFoundException(String message, String errorCode) {
    super(message, errorCode, 404);
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public NotFoundException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, 404, cause);
  }
}
