package com.snowrepo.domain;

import java.io.Serializable;

/**
 * Base class for all value objects in the domain.
 * Value objects are immutable objects that represent a value.
 * They are compared by their attributes, not by identity.
 */
public abstract class ValueObject implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Subclasses must implement equals to compare value objects by their attributes.
   *
   * @param o the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public abstract boolean equals(Object o);

  /**
   * Subclasses must implement hashCode to ensure value objects with the same attributes
   * have the same hash code.
   *
   * @return the hash code
   */
  @Override
  public abstract int hashCode();

  /**
   * Subclasses should implement toString to provide a meaningful string representation.
   *
   * @return the string representation
   */
  @Override
  public abstract String toString();
}
