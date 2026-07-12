package com.snowrepo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AggregateRoot Tests")
class AggregateRootTest {

  private TestAggregateRoot aggregateRoot;

  @BeforeEach
  void setUp() {
    aggregateRoot = new TestAggregateRoot();
  }

  @Test
  @DisplayName("Should create aggregate root with generated ID")
  void testCreateAggregateRootWithGeneratedId() {
    assertNotNull(aggregateRoot.getId());
    assertNotNull(aggregateRoot.getCreatedAt());
    assertNotNull(aggregateRoot.getUpdatedAt());
    assertEquals(0L, aggregateRoot.getVersion());
  }

  @Test
  @DisplayName("Should create aggregate root with provided ID")
  void testCreateAggregateRootWithProvidedId() {
    UUID providedId = UUID.randomUUID();
    TestAggregateRoot root = new TestAggregateRoot(providedId);

    assertEquals(providedId, root.getId());
    assertNotNull(root.getCreatedAt());
    assertNotNull(root.getUpdatedAt());
  }

  @Test
  @DisplayName("Should add domain event")
  void testAddDomainEvent() {
    TestDomainEvent event = new TestDomainEvent(aggregateRoot.getId(), "TestAggregate");

    aggregateRoot.addDomainEvent(event);

    assertTrue(aggregateRoot.hasDomainEvents());
    assertEquals(1, aggregateRoot.getDomainEvents().size());
    assertEquals(event, aggregateRoot.getDomainEvents().get(0));
  }

  @Test
  @DisplayName("Should not add null domain event")
  void testAddNullDomainEvent() {
    aggregateRoot.addDomainEvent(null);

    assertFalse(aggregateRoot.hasDomainEvents());
    assertEquals(0, aggregateRoot.getDomainEvents().size());
  }

  @Test
  @DisplayName("Should clear domain events")
  void testClearDomainEvents() {
    TestDomainEvent event = new TestDomainEvent(aggregateRoot.getId(), "TestAggregate");
    aggregateRoot.addDomainEvent(event);

    assertTrue(aggregateRoot.hasDomainEvents());

    aggregateRoot.clearDomainEvents();

    assertFalse(aggregateRoot.hasDomainEvents());
    assertEquals(0, aggregateRoot.getDomainEvents().size());
  }

  @Test
  @DisplayName("Should mark as updated")
  void testMarkAsUpdated() {
    UUID userId = UUID.randomUUID();
    var originalUpdatedAt = aggregateRoot.getUpdatedAt();

    aggregateRoot.markAsUpdated(userId);

    assertEquals(userId, aggregateRoot.getUpdatedBy());
    assertNotNull(aggregateRoot.getUpdatedAt());
  }

  @Test
  @DisplayName("Should increment version")
  void testIncrementVersion() {
    assertEquals(0L, aggregateRoot.getVersion());

    aggregateRoot.incrementVersion();

    assertEquals(1L, aggregateRoot.getVersion());

    aggregateRoot.incrementVersion();

    assertEquals(2L, aggregateRoot.getVersion());
  }

  @Test
  @DisplayName("Should return unmodifiable domain events list")
  void testGetDomainEventsReturnsUnmodifiableList() {
    TestDomainEvent event = new TestDomainEvent(aggregateRoot.getId(), "TestAggregate");
    aggregateRoot.addDomainEvent(event);

    var events = aggregateRoot.getDomainEvents();

    assertThrows(UnsupportedOperationException.class, () -> events.add(event));
  }

  @Test
  @DisplayName("Should be equal by ID")
  void testEqualityById() {
    UUID id = UUID.randomUUID();
    TestAggregateRoot root1 = new TestAggregateRoot(id);
    TestAggregateRoot root2 = new TestAggregateRoot(id);

    assertEquals(root1, root2);
    assertEquals(root1.hashCode(), root2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal with different IDs")
  void testInequalityWithDifferentIds() {
    TestAggregateRoot root1 = new TestAggregateRoot();
    TestAggregateRoot root2 = new TestAggregateRoot();

    assertNotEquals(root1, root2);
    assertNotEquals(root1.hashCode(), root2.hashCode());
  }

  /**
   * Test implementation of AggregateRoot for testing purposes.
   */
  static class TestAggregateRoot extends AggregateRoot {

    TestAggregateRoot() {
      super();
    }

    TestAggregateRoot(UUID id) {
      super(id);
    }
  }

  /**
   * Test implementation of DomainEvent for testing purposes.
   */
  static class TestDomainEvent extends DomainEvent {

    TestDomainEvent(UUID aggregateId, String aggregateType) {
      super(aggregateId, aggregateType);
    }
  }
}
