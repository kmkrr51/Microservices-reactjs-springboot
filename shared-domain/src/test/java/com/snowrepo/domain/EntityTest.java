package com.snowrepo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Entity Tests")
class EntityTest {

  private TestEntity entity;

  @BeforeEach
  void setUp() {
    entity = new TestEntity();
  }

  @Test
  @DisplayName("Should create entity with generated ID")
  void testCreateEntityWithGeneratedId() {
    assertNotNull(entity.getId());
  }

  @Test
  @DisplayName("Should create entity with provided ID")
  void testCreateEntityWithProvidedId() {
    UUID providedId = UUID.randomUUID();
    TestEntity testEntity = new TestEntity(providedId);

    assertEquals(providedId, testEntity.getId());
  }

  @Test
  @DisplayName("Should be equal by ID")
  void testEqualityById() {
    UUID id = UUID.randomUUID();
    TestEntity entity1 = new TestEntity(id);
    TestEntity entity2 = new TestEntity(id);

    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal with different IDs")
  void testInequalityWithDifferentIds() {
    TestEntity entity1 = new TestEntity();
    TestEntity entity2 = new TestEntity();

    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal with null ID")
  void testInequalityWithNullId() {
    TestEntity entity1 = new TestEntity();
    TestEntity entity2 = new TestEntity();
    entity2.setId(null);

    assertNotEquals(entity1, entity2);
  }

  @Test
  @DisplayName("Should have consistent hash code")
  void testHashCodeConsistency() {
    int hashCode1 = entity.hashCode();
    int hashCode2 = entity.hashCode();

    assertEquals(hashCode1, hashCode2);
  }

  /**
   * Test implementation of Entity for testing purposes.
   */
  static class TestEntity extends Entity {

    TestEntity() {
      super();
    }

    TestEntity(UUID id) {
      super(id);
    }
  }
}
