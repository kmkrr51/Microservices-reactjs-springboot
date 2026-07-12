package com.snowrepo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DateTimeUtils Tests")
class DateTimeUtilsTest {

  @Test
  @DisplayName("Should get current date and time")
  void testNow() {
    LocalDateTime now = DateTimeUtils.now();
    assertNotNull(now);
  }

  @Test
  @DisplayName("Should get today's date")
  void testToday() {
    LocalDate today = DateTimeUtils.today();
    assertNotNull(today);
    assertEquals(LocalDate.now(), today);
  }

  @Test
  @DisplayName("Should convert LocalDateTime to ISO string")
  void testToIsoString() {
    LocalDateTime dateTime = LocalDateTime.of(2026, 7, 12, 15, 30, 45);
    String iso = DateTimeUtils.toIsoString(dateTime);
    assertEquals("2026-07-12T15:30:45", iso);
  }

  @Test
  @DisplayName("Should parse ISO string to LocalDateTime")
  void testParseDateTime() {
    LocalDateTime dateTime = DateTimeUtils.parseDateTime("2026-07-12T15:30:45");
    assertNotNull(dateTime);
    assertEquals(2026, dateTime.getYear());
    assertEquals(7, dateTime.getMonthValue());
    assertEquals(12, dateTime.getDayOfMonth());
  }

  @Test
  @DisplayName("Should check if date is in the past")
  void testIsPast() {
    LocalDate pastDate = LocalDate.now().minusDays(1);
    LocalDate futureDate = LocalDate.now().plusDays(1);
    
    assertTrue(DateTimeUtils.isPast(pastDate));
    assertFalse(DateTimeUtils.isPast(futureDate));
  }

  @Test
  @DisplayName("Should check if date is in the future")
  void testIsFuture() {
    LocalDate pastDate = LocalDate.now().minusDays(1);
    LocalDate futureDate = LocalDate.now().plusDays(1);
    
    assertFalse(DateTimeUtils.isFuture(pastDate));
    assertTrue(DateTimeUtils.isFuture(futureDate));
  }

  @Test
  @DisplayName("Should check if date is today")
  void testIsToday() {
    LocalDate today = LocalDate.now();
    LocalDate yesterday = LocalDate.now().minusDays(1);
    
    assertTrue(DateTimeUtils.isToday(today));
    assertFalse(DateTimeUtils.isToday(yesterday));
  }
}
