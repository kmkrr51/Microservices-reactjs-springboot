package com.snowrepo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events.
 * Domain events represent something that happened in the domain.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class DomainEvent implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Unique identifier for the domain event.
   */
  private UUID eventId;

  /**
   * Aggregate root ID that this event belongs to.
   */
  private UUID aggregateId;

  /**
   * Type of the aggregate root.
   */
  private String aggregateType;

  /**
   * Timestamp when the event occurred.
   */
  private LocalDateTime occurredAt;

  /**
   * Version of the aggregate root when the event occurred.
   */
  private Long aggregateVersion;

  /**
   * Correlation ID for tracing related events.
   */
  private UUID correlationId;

  /**
   * Causation ID for tracing the cause of this event.
   */
  private UUID causationId;

  /**
   * User ID who triggered the event.
   */
  private UUID userId;

  /**
   * Protected constructor for subclasses.
   *
   * @param aggregateId the aggregate root ID
   * @param aggregateType the aggregate root type
   */
  protected DomainEvent(UUID aggregateId, String aggregateType) {
    this.eventId = UUID.randomUUID();
    this.aggregateId = aggregateId;
    this.aggregateType = aggregateType;
    this.occurredAt = LocalDateTime.now();
    this.aggregateVersion = 1L;
    this.correlationId = UUID.randomUUID();
  }

  /**
   * Protected constructor with all parameters.
   *
   * @param aggregateId the aggregate root ID
   * @param aggregateType the aggregate root type
   * @param aggregateVersion the aggregate root version
   * @param correlationId the correlation ID
   * @param userId the user ID
   */
  protected DomainEvent(UUID aggregateId, String aggregateType, Long aggregateVersion,
                        UUID correlationId, UUID userId) {
    this.eventId = UUID.randomUUID();
    this.aggregateId = aggregateId;
    this.aggregateType = aggregateType;
    this.occurredAt = LocalDateTime.now();
    this.aggregateVersion = aggregateVersion;
    this.correlationId = correlationId;
    this.userId = userId;
  }

  /**
   * Returns the event type name (class simple name).
   *
   * @return the event type name
   */
  public String getEventType() {
    return this.getClass().getSimpleName();
  }

  /**
   * Sets the causation ID for this event.
   *
   * @param causationId the causation ID
   */
  public void setCausationId(UUID causationId) {
    this.causationId = causationId;
  }

  /**
   * Sets the user ID who triggered the event.
   *
   * @param userId the user ID
   */
  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DomainEvent)) {
      return false;
    }
    DomainEvent that = (DomainEvent) o;
    return eventId != null && eventId.equals(that.eventId);
  }

  @Override
  public int hashCode() {
    return eventId != null ? eventId.hashCode() : 0;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "eventId=" + eventId + ", aggregateId="
        + aggregateId + ", occurredAt=" + occurredAt + '}';
  }
}
