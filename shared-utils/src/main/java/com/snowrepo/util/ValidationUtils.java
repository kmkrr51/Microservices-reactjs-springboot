package com.snowrepo.util;

import com.snowrepo.exception.ValidationException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for validation operations.
 */
public class ValidationUtils {

  private ValidationUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  /**
   * Validates that an object is not null.
   *
   * @param obj the object to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the object is null
   */
  public static void notNull(Object obj, String fieldName) {
    if (obj == null) {
      throw new ValidationException(fieldName + " cannot be null", "FIELD_NULL");
    }
  }

  /**
   * Validates that a string is not null or empty.
   *
   * @param str the string to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the string is null or empty
   */
  public static void notEmpty(String str, String fieldName) {
    if (StringUtils.isEmpty(str)) {
      throw new ValidationException(fieldName + " cannot be empty", "FIELD_EMPTY");
    }
  }

  /**
   * Validates that a string is not null, empty, or blank.
   *
   * @param str the string to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the string is blank
   */
  public static void notBlank(String str, String fieldName) {
    if (StringUtils.isBlank(str)) {
      throw new ValidationException(fieldName + " cannot be blank", "FIELD_BLANK");
    }
  }

  /**
   * Validates that a collection is not null or empty.
   *
   * @param collection the collection to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the collection is null or empty
   */
  public static void notEmpty(Collection<?> collection, String fieldName) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new ValidationException(fieldName + " cannot be empty", "COLLECTION_EMPTY");
    }
  }

  /**
   * Validates that a map is not null or empty.
   *
   * @param map the map to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the map is null or empty
   */
  public static void notEmpty(Map<?, ?> map, String fieldName) {
    if (CollectionUtils.isEmpty(map)) {
      throw new ValidationException(fieldName + " cannot be empty", "MAP_EMPTY");
    }
  }

  /**
   * Validates that a string is a valid email address.
   *
   * @param email the email to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the email is invalid
   */
  public static void validEmail(String email, String fieldName) {
    if (!StringUtils.isValidEmail(email)) {
      throw new ValidationException(fieldName + " is not a valid email", "INVALID_EMAIL");
    }
  }

  /**
   * Validates that a string is a valid UUID.
   *
   * @param uuid the UUID string to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the UUID is invalid
   */
  public static void validUUID(String uuid, String fieldName) {
    if (!StringUtils.isValidUUID(uuid)) {
      throw new ValidationException(fieldName + " is not a valid UUID", "INVALID_UUID");
    }
  }

  /**
   * Validates that a UUID is not null.
   *
   * @param uuid the UUID to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the UUID is null
   */
  public static void notNull(UUID uuid, String fieldName) {
    if (uuid == null) {
      throw new ValidationException(fieldName + " cannot be null", "UUID_NULL");
    }
  }

  /**
   * Validates that a number is positive.
   *
   * @param number the number to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the number is not positive
   */
  public static void positive(long number, String fieldName) {
    if (number <= 0) {
      throw new ValidationException(fieldName + " must be positive", "NOT_POSITIVE");
    }
  }

  /**
   * Validates that a number is non-negative.
   *
   * @param number the number to validate
   * @param fieldName the field name for error message
   * @throws ValidationException if the number is negative
   */
  public static void nonNegative(long number, String fieldName) {
    if (number < 0) {
      throw new ValidationException(fieldName + " cannot be negative", "NEGATIVE");
    }
  }

  /**
   * Validates that a condition is true.
   *
   * @param condition the condition to validate
   * @param message the error message
   * @param errorCode the error code
   * @throws ValidationException if the condition is false
   */
  public static void isTrue(boolean condition, String message, String errorCode) {
    if (!condition) {
      throw new ValidationException(message, errorCode);
    }
  }

  /**
   * Validates that a condition is false.
   *
   * @param condition the condition to validate
   * @param message the error message
   * @param errorCode the error code
   * @throws ValidationException if the condition is true
   */
  public static void isFalse(boolean condition, String message, String errorCode) {
    if (condition) {
      throw new ValidationException(message, errorCode);
    }
  }

  /**
   * Validates that a string matches a pattern.
   *
   * @param str the string to validate
   * @param pattern the regex pattern
   * @param fieldName the field name for error message
   * @throws ValidationException if the string doesn't match the pattern
   */
  public static void matches(String str, String pattern, String fieldName) {
    if (StringUtils.isNotEmpty(str) && !str.matches(pattern)) {
      throw new ValidationException(fieldName + " format is invalid", "INVALID_FORMAT");
    }
  }

  /**
   * Validates that a string has a specific length.
   *
   * @param str the string to validate
   * @param minLength the minimum length
   * @param maxLength the maximum length
   * @param fieldName the field name for error message
   * @throws ValidationException if the string length is invalid
   */
  public static void length(String str, int minLength, int maxLength, String fieldName) {
    if (StringUtils.isNotEmpty(str)) {
      int length = str.length();
      if (length < minLength || length > maxLength) {
        throw new ValidationException(
            fieldName + " length must be between " + minLength + " and " + maxLength,
            "INVALID_LENGTH"
        );
      }
    }
  }
}
