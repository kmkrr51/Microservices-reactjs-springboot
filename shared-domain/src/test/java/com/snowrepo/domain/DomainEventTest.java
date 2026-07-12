package com.snowrepo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DomainEvent Tests")
class DomainEventTest {

  private UUID aggregateId;
  private TestDomainEvent event;

  @BeforeEach
  void setUp() {
    aggregateId = UUID.randomUUID();
    event = new TestDomainEvent(aggregateId, "TestAggregate");
  }

  @Test
  @DisplayName("Should create domain event with required fields")
  void testCreateDomainEvent() {
    assertNotNull(event.getEventId());
    assertEquals(aggregateId, event.getAggregateId());
    assertEquals("TestAggregate", event.getAggregateType());
    assertNotNull(event.getOccurredAt());
    assertEquals(1L, event.getAggregateVersion());
    assertNotNull(event.getCorrelationId());
  }

  @Test
  @DisplayName("Should return event type name")
  void testGetEventType() {
    assertEquals("TestDomainEvent", event.getEventType());
  }

  @Test
  @DisplayName("Should set causation ID")
  void testSetCausationId() {
    UUID causationId = UUID.randomUUID();

    event.setCausationId(causationId);

    assertEquals(causationId, event.getCausationId());
  }

  @Test
  @DisplayName("Should set user ID")
  void testSetUserId() {
    UUID userId = UUID.randomUUID();

    event.setUserId(userId);

    assertEquals(userId, event.getUserId());
  }

  @Test
  @DisplayName("Should be equal by event ID")
  void testEqualityByEventId() {
    UUID eventId = UUID.randomUUID();
    TestDomainEvent event1 = new TestDomainEvent(aggregateId, "TestAggregate");
    TestDomainEvent event2 = new TestDomainEvent(aggregateId, "TestAggregate");

    // Both have different event IDs, so they should not be equal
    assertNotEquals(event1, event2);
  }

  @Test
  @DisplayName("Should have consistent hash code")
  void testHashCodeConsistency() {
    int hashCode1 = event.hashCode();
    int hashCode2 = event.hashCode();

    assertEquals(hashCode1, hashCode2);
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
