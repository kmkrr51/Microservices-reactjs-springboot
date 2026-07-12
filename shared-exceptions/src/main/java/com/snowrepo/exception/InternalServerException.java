package com.snowrepo.exception;

/**
 * Exception for internal server errors.
 * Thrown when an unexpected error occurs on the server.
 */
public class InternalServerException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public InternalServerException(String message, String errorCode) {
    super(message, errorCode, 500);
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public InternalServerException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, 500, cause);
  }
}
