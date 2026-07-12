package com.snowrepo.util;

import com.snowrepo.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Collection and Validation Utils Tests")
class UtilsTest {

  @Test
  @DisplayName("CollectionUtils should identify empty collections")
  void testCollectionUtilsIsEmpty() {
    assertTrue(CollectionUtils.isEmpty(null));
    assertTrue(CollectionUtils.isEmpty(Arrays.asList()));
    assertFalse(CollectionUtils.isEmpty(Arrays.asList("a", "b")));
  }

  @Test
  @DisplayName("CollectionUtils should get first and last elements")
  void testCollectionUtilsGetFirstLast() {
    List<String> list = Arrays.asList("a", "b", "c");
    assertEquals("a", CollectionUtils.getFirst(list));
    assertEquals("c", CollectionUtils.getLast(list));
    assertNull(CollectionUtils.getFirst(Arrays.asList()));
  }

  @Test
  @DisplayName("CollectionUtils should check collection size")
  void testCollectionUtilsHasSize() {
    List<String> list = Arrays.asList("a", "b", "c");
    assertTrue(CollectionUtils.hasSize(list, 3));
    assertFalse(CollectionUtils.hasSize(list, 2));
    assertTrue(CollectionUtils.hasSingleElement(Arrays.asList("a")));
  }

  @Test
  @DisplayName("ValidationUtils should validate not null")
  void testValidationUtilsNotNull() {
    assertDoesNotThrow(() -> ValidationUtils.notNull("test", "field"));
    assertThrows(ValidationException.class, () -> ValidationUtils.notNull(null, "field"));
  }

  @Test
  @DisplayName("ValidationUtils should validate not empty")
  void testValidationUtilsNotEmpty() {
    assertDoesNotThrow(() -> ValidationUtils.notEmpty("test", "field"));
    assertThrows(ValidationException.class, () -> ValidationUtils.notEmpty("", "field"));
    assertThrows(ValidationException.class, () -> ValidationUtils.notEmpty(null, "field"));
  }

  @Test
  @DisplayName("ValidationUtils should validate not blank")
  void testValidationUtilsNotBlank() {
    assertDoesNotThrow(() -> ValidationUtils.notBlank("test", "field"));
    assertThrows(ValidationException.class, () -> ValidationUtils.notBlank("   ", "field"));
    assertThrows(ValidationException.class, () -> ValidationUtils.notBlank("", "field"));
  }

  @Test
  @DisplayName("ValidationUtils should validate email")
  void testValidationUtilsValidEmail() {
    assertDoesNotThrow(() -> ValidationUtils.validEmail("test@example.com", "email"));
    assertThrows(ValidationException.class,
        () -> ValidationUtils.validEmail("invalid", "email"));
  }

  @Test
  @DisplayName("ValidationUtils should validate UUID")
  void testValidationUtilsValidUUID() {
    String validUUID = "550e8400-e29b-41d4-a716-446655440000";
    assertDoesNotThrow(() -> ValidationUtils.validUUID(validUUID, "id"));
    assertThrows(ValidationException.class,
        () -> ValidationUtils.validUUID("invalid", "id"));
  }

  @Test
  @DisplayName("ValidationUtils should validate positive numbers")
  void testValidationUtilsPositive() {
    assertDoesNotThrow(() -> ValidationUtils.positive(5, "count"));
    assertThrows(ValidationException.class, () -> ValidationUtils.positive(0, "count"));
    assertThrows(ValidationException.class, () -> ValidationUtils.positive(-1, "count"));
  }

  @Test
  @DisplayName("ValidationUtils should validate conditions")
  void testValidationUtilsConditions() {
    assertDoesNotThrow(() -> ValidationUtils.isTrue(true, "error", "CODE"));
    assertThrows(ValidationException.class,
        () -> ValidationUtils.isTrue(false, "error", "CODE"));
    assertDoesNotThrow(() -> ValidationUtils.isFalse(false, "error", "CODE"));
    assertThrows(ValidationException.class,
        () -> ValidationUtils.isFalse(true, "error", "CODE"));
  }

  @Test
  @DisplayName("ValidationUtils should validate string length")
  void testValidationUtilsLength() {
    assertDoesNotThrow(() -> ValidationUtils.length("test", 1, 10, "field"));
    assertThrows(ValidationException.class,
        () -> ValidationUtils.length("test", 5, 10, "field"));
  }
}
