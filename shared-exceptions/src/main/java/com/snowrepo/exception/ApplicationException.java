package com.snowrepo.exception;

/**
 * Base exception class for all application exceptions.
 * All custom exceptions should extend this class.
 */
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * HTTP status code for the exception.
   */
  private final int statusCode;

  /**
   * Error code for the exception.
   */
  private final String errorCode;

  /**
   * Constructor with message and error code.
   *
   * @param message the exception message
   * @param errorCode the error code
   */
  public ApplicationException(String message, String errorCode) {
    super(message);
    this.statusCode = 500;
    this.errorCode = errorCode;
  }

  /**
   * Constructor with message, error code, and status code.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param statusCode the HTTP status code
   */
  public ApplicationException(String message, String errorCode, int statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.errorCode = errorCode;
  }

  /**
   * Constructor with message, error code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param cause the cause of the exception
   */
  public ApplicationException(String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.statusCode = 500;
    this.errorCode = errorCode;
  }

  /**
   * Constructor with message, error code, status code, and cause.
   *
   * @param message the exception message
   * @param errorCode the error code
   * @param statusCode the HTTP status code
   * @param cause the cause of the exception
   */
  public ApplicationException(String message, String errorCode, int statusCode,
                              Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
    this.errorCode = errorCode;
  }

  /**
   * Gets the HTTP status code.
   *
   * @return the status code
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Gets the error code.
   *
   * @return the error code
   */
  public String getErrorCode() {
    return errorCode;
  }
}
