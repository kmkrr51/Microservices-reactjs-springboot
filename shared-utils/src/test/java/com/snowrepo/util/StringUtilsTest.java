package com.snowrepo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StringUtils Tests")
class StringUtilsTest {

  @Test
  @DisplayName("Should identify empty strings")
  void testIsEmpty() {
    assertTrue(StringUtils.isEmpty(null));
    assertTrue(StringUtils.isEmpty(""));
    assertFalse(StringUtils.isEmpty("test"));
  }

  @Test
  @DisplayName("Should identify blank strings")
  void testIsBlank() {
    assertTrue(StringUtils.isBlank(null));
    assertTrue(StringUtils.isBlank(""));
    assertTrue(StringUtils.isBlank("   "));
    assertFalse(StringUtils.isBlank("test"));
  }

  @Test
  @DisplayName("Should validate email addresses")
  void testIsValidEmail() {
    assertTrue(StringUtils.isValidEmail("test@example.com"));
    assertTrue(StringUtils.isValidEmail("user+tag@domain.co.uk"));
    assertFalse(StringUtils.isValidEmail("invalid"));
    assertFalse(StringUtils.isValidEmail(""));
    assertFalse(StringUtils.isValidEmail(null));
  }

  @Test
  @DisplayName("Should validate UUIDs")
  void testIsValidUUID() {
    assertTrue(StringUtils.isValidUUID("550e8400-e29b-41d4-a716-446655440000"));
    assertFalse(StringUtils.isValidUUID("invalid-uuid"));
    assertFalse(StringUtils.isValidUUID(""));
    assertFalse(StringUtils.isValidUUID(null));
  }

  @Test
  @DisplayName("Should capitalize strings")
  void testCapitalize() {
    assertEquals("Test", StringUtils.capitalize("test"));
    assertEquals("TEST", StringUtils.capitalize("TEST"));
    assertNull(StringUtils.capitalize(null));
  }

  @Test
  @DisplayName("Should join strings")
  void testJoin() {
    assertEquals("a,b,c", StringUtils.join(",", "a", "b", "c"));
    assertEquals("", StringUtils.join(","));
    assertEquals("test", StringUtils.join(",", "test"));
  }

  @Test
  @DisplayName("Should repeat strings")
  void testRepeat() {
    assertEquals("aaa", StringUtils.repeat("a", 3));
    assertEquals("", StringUtils.repeat("a", 0));
    assertEquals("", StringUtils.repeat(null, 3));
  }
}
