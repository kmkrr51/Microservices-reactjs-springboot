package com.snowrepo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for date and time operations.
 */
public class DateTimeUtils {

  private static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
  private static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  private static final String ISO_DATETIME_WITH_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  private DateTimeUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  /**
   * Gets the current date and time.
   *
   * @return the current LocalDateTime
   */
  public static LocalDateTime now() {
    return LocalDateTime.now();
  }

  /**
   * Gets the current date.
   *
   * @return the current LocalDate
   */
  public static LocalDate today() {
    return LocalDate.now();
  }

  /**
   * Converts LocalDateTime to ISO format string.
   *
   * @param dateTime the LocalDateTime to convert
   * @return the ISO format string
   */
  public static String toIsoString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  /**
   * Converts LocalDate to ISO format string.
   *
   * @param date the LocalDate to convert
   * @return the ISO format string
   */
  public static String toIsoString(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  /**
   * Parses ISO format string to LocalDateTime.
   *
   * @param dateTimeString the ISO format string
   * @return the LocalDateTime
   */
  public static LocalDateTime parseDateTime(String dateTimeString) {
    if (dateTimeString == null || dateTimeString.isEmpty()) {
      return null;
    }
    return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  /**
   * Parses ISO format string to LocalDate.
   *
   * @param dateString the ISO format string
   * @return the LocalDate
   */
  public static LocalDate parseDate(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      return null;
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
  }

  /**
   * Converts Date to LocalDateTime.
   *
   * @param date the Date to convert
   * @return the LocalDateTime
   */
  public static LocalDateTime toLocalDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * Converts LocalDateTime to Date.
   *
   * @param dateTime the LocalDateTime to convert
   * @return the Date
   */
  public static Date toDate(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Checks if a date is in the past.
   *
   * @param date the date to check
   * @return true if the date is in the past, false otherwise
   */
  public static boolean isPast(LocalDate date) {
    return date != null && date.isBefore(LocalDate.now());
  }

  /**
   * Checks if a date is in the future.
   *
   * @param date the date to check
   * @return true if the date is in the future, false otherwise
   */
  public static boolean isFuture(LocalDate date) {
    return date != null && date.isAfter(LocalDate.now());
  }

  /**
   * Checks if a date is today.
   *
   * @param date the date to check
   * @return true if the date is today, false otherwise
   */
  public static boolean isToday(LocalDate date) {
    return date != null && date.equals(LocalDate.now());
  }
}
