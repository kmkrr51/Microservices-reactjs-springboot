package com.snowrepo.logging;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * Utility class for structured logging with correlation IDs.
 */
public class LoggingUtils {

  private static final String CORRELATION_ID = "correlationId";
  private static final String USER_ID = "userId";
  private static final String REQUEST_ID = "requestId";

  private LoggingUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  /**
   * Sets correlation ID in MDC.
   *
   * @param correlationId the correlation ID
   */
  public static void setCorrelationId(UUID correlationId) {
    if (correlationId != null) {
      MDC.put(CORRELATION_ID, correlationId.toString());
    }
  }

  /**
   * Gets correlation ID from MDC.
   *
   * @return the correlation ID or null
   */
  public static String getCorrelationId() {
    return MDC.get(CORRELATION_ID);
  }

  /**
   * Sets user ID in MDC.
   *
   * @param userId the user ID
   */
  public static void setUserId(UUID userId) {
    if (userId != null) {
      MDC.put(USER_ID, userId.toString());
    }
  }

  /**
   * Gets user ID from MDC.
   *
   * @return the user ID or null
   */
  public static String getUserId() {
    return MDC.get(USER_ID);
  }

  /**
   * Sets request ID in MDC.
   *
   * @param requestId the request ID
   */
  public static void setRequestId(UUID requestId) {
    if (requestId != null) {
      MDC.put(REQUEST_ID, requestId.toString());
    }
  }

  /**
   * Gets request ID from MDC.
   *
   * @return the request ID or null
   */
  public static String getRequestId() {
    return MDC.get(REQUEST_ID);
  }

  /**
   * Clears all MDC values.
   */
  public static void clearMDC() {
    MDC.clear();
  }

  /**
   * Logs info message with correlation context.
   *
   * @param logger the logger
   * @param message the message
   */
  public static void logInfo(Logger logger, String message) {
    logger.info(message);
  }

  /**
   * Logs error message with correlation context.
   *
   * @param logger the logger
   * @param message the message
   * @param throwable the exception
   */
  public static void logError(Logger logger, String message, Throwable throwable) {
    logger.error(message, throwable);
  }

  /**
   * Logs warning message with correlation context.
   *
   * @param logger the logger
   * @param message the message
   */
  public static void logWarn(Logger logger, String message) {
    logger.warn(message);
  }

  /**
   * Logs debug message with correlation context.
   *
   * @param logger the logger
   * @param message the message
   */
  public static void logDebug(Logger logger, String message) {
    logger.debug(message);
  }
}
