package com.snowrepo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

/**
 * Base class for all entities in the domain.
 * Entities have an identity and can change over time.
 * They are compared by their identity, not by their attributes.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class Entity implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Unique identifier for the entity.
   */
  private UUID id;

  /**
   * Protected constructor for subclasses.
   */
  protected Entity() {
    this.id = UUID.randomUUID();
  }

  /**
   * Protected constructor with ID for subclasses.
   *
   * @param id the entity ID
   */
  protected Entity(UUID id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Entity)) {
      return false;
    }
    Entity entity = (Entity) o;
    return id != null && id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "id=" + id + '}';
  }
}
