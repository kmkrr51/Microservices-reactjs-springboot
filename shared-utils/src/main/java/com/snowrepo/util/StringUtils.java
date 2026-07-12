package com.snowrepo.util;

import java.util.UUID;

/**
 * Utility class for string operations.
 */
public class StringUtils {

  private StringUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  /**
   * Checks if a string is null or empty.
   *
   * @param str the string to check
   * @return true if the string is null or empty, false otherwise
   */
  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * Checks if a string is null, empty, or contains only whitespace.
   *
   * @param str the string to check
   * @return true if the string is blank, false otherwise
   */
  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  /**
   * Checks if a string is not null and not empty.
   *
   * @param str the string to check
   * @return true if the string is not empty, false otherwise
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * Checks if a string is not null, not empty, and not blank.
   *
   * @param str the string to check
   * @return true if the string is not blank, false otherwise
   */
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * Trims a string, returning null if the result is empty.
   *
   * @param str the string to trim
   * @return the trimmed string or null if empty
   */
  public static String trimToNull(String str) {
    if (str == null) {
      return null;
    }
    String trimmed = str.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }

  /**
   * Trims a string, returning empty string if the result is empty.
   *
   * @param str the string to trim
   * @return the trimmed string or empty string if null
   */
  public static String trimToEmpty(String str) {
    return str == null ? "" : str.trim();
  }

  /**
   * Capitalizes the first character of a string.
   *
   * @param str the string to capitalize
   * @return the capitalized string
   */
  public static String capitalize(String str) {
    if (isEmpty(str)) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /**
   * Converts a string to lowercase.
   *
   * @param str the string to convert
   * @return the lowercase string
   */
  public static String toLowercase(String str) {
    return str == null ? null : str.toLowerCase();
  }

  /**
   * Converts a string to uppercase.
   *
   * @param str the string to convert
   * @return the uppercase string
   */
  public static String toUppercase(String str) {
    return str == null ? null : str.toUpperCase();
  }

  /**
   * Checks if a string contains only alphanumeric characters.
   *
   * @param str the string to check
   * @return true if the string is alphanumeric, false otherwise
   */
  public static boolean isAlphanumeric(String str) {
    if (isEmpty(str)) {
      return false;
    }
    return str.matches("^[a-zA-Z0-9]+$");
  }

  /**
   * Checks if a string is a valid email address.
   *
   * @param email the email to check
   * @return true if the email is valid, false otherwise
   */
  public static boolean isValidEmail(String email) {
    if (isEmpty(email)) {
      return false;
    }
    return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
  }

  /**
   * Checks if a string is a valid UUID.
   *
   * @param uuid the UUID string to check
   * @return true if the string is a valid UUID, false otherwise
   */
  public static boolean isValidUUID(String uuid) {
    if (isEmpty(uuid)) {
      return false;
    }
    try {
      UUID.fromString(uuid);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Joins multiple strings with a separator.
   *
   * @param separator the separator
   * @param strings the strings to join
   * @return the joined string
   */
  public static String join(String separator, String... strings) {
    if (strings == null || strings.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strings.length; i++) {
      if (i > 0) {
        sb.append(separator);
      }
      sb.append(strings[i]);
    }
    return sb.toString();
  }

  /**
   * Repeats a string a specified number of times.
   *
   * @param str the string to repeat
   * @param count the number of times to repeat
   * @return the repeated string
   */
  public static String repeat(String str, int count) {
    if (isEmpty(str) || count <= 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) {
      sb.append(str);
    }
    return sb.toString();
  }
}
