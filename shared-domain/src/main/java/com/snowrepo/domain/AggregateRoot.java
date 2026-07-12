package com.snowrepo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Base class for all aggregate roots in the domain.
 * Aggregate roots are the entry points to aggregates and manage domain events.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class AggregateRoot implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Unique identifier for the aggregate root.
   */
  private UUID id;

  /**
   * Timestamp when the aggregate root was created.
   */
  private LocalDateTime createdAt;

  /**
   * User ID who created the aggregate root.
   */
  private UUID createdBy;

  /**
   * Timestamp when the aggregate root was last updated.
   */
  private LocalDateTime updatedAt;

  /**
   * User ID who last updated the aggregate root.
   */
  private UUID updatedBy;

  /**
   * Version number for optimistic locking.
   */
  private Long version;

  /**
   * List of domain events that occurred on this aggregate root.
   */
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final List<DomainEvent> domainEvents = new ArrayList<>();

  /**
   * Protected constructor for subclasses.
   */
  protected AggregateRoot() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.version = 0L;
  }

  /**
   * Protected constructor with ID for subclasses.
   *
   * @param id the aggregate root ID
   */
  protected AggregateRoot(UUID id) {
    this.id = id;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.version = 0L;
  }

  /**
   * Adds a domain event to the aggregate root.
   *
   * @param event the domain event to add
   */
  protected void addDomainEvent(DomainEvent event) {
    if (event != null) {
      this.domainEvents.add(event);
    }
  }

  /**
   * Returns an unmodifiable list of domain events.
   *
   * @return list of domain events
   */
  public List<DomainEvent> getDomainEvents() {
    return Collections.unmodifiableList(this.domainEvents);
  }

  /**
   * Clears all domain events from the aggregate root.
   */
  public void clearDomainEvents() {
    this.domainEvents.clear();
  }

  /**
   * Checks if the aggregate root has any domain events.
   *
   * @return true if there are domain events, false otherwise
   */
  public boolean hasDomainEvents() {
    return !this.domainEvents.isEmpty();
  }

  /**
   * Updates the timestamp and user for the last update.
   *
   * @param updatedBy the user ID who updated the aggregate root
   */
  protected void markAsUpdated(UUID updatedBy) {
    this.updatedAt = LocalDateTime.now();
    this.updatedBy = updatedBy;
  }

  /**
   * Increments the version number for optimistic locking.
   */
  protected void incrementVersion() {
    this.version++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AggregateRoot)) {
      return false;
    }
    AggregateRoot that = (AggregateRoot) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "id=" + id + ", version=" + version + '}';
  }
}
