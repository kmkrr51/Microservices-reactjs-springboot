package com.snowrepo.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class for collection operations.
 */
public class CollectionUtils {

  private CollectionUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  /**
   * Checks if a collection is null or empty.
   *
   * @param collection the collection to check
   * @return true if the collection is null or empty, false otherwise
   */
  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  /**
   * Checks if a collection is not null and not empty.
   *
   * @param collection the collection to check
   * @return true if the collection is not empty, false otherwise
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  /**
   * Checks if a map is null or empty.
   *
   * @param map the map to check
   * @return true if the map is null or empty, false otherwise
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  /**
   * Checks if a map is not null and not empty.
   *
   * @param map the map to check
   * @return true if the map is not empty, false otherwise
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  /**
   * Gets the size of a collection, returning 0 if null.
   *
   * @param collection the collection to check
   * @return the size of the collection or 0 if null
   */
  public static int size(Collection<?> collection) {
    return collection == null ? 0 : collection.size();
  }

  /**
   * Gets the size of a map, returning 0 if null.
   *
   * @param map the map to check
   * @return the size of the map or 0 if null
   */
  public static int size(Map<?, ?> map) {
    return map == null ? 0 : map.size();
  }

  /**
   * Checks if a collection contains a specific element.
   *
   * @param collection the collection to check
   * @param element the element to find
   * @return true if the collection contains the element, false otherwise
   */
  public static boolean contains(Collection<?> collection, Object element) {
    return collection != null && collection.contains(element);
  }

  /**
   * Checks if a collection contains all elements from another collection.
   *
   * @param collection the collection to check
   * @param elements the elements to find
   * @return true if the collection contains all elements, false otherwise
   */
  public static boolean containsAll(Collection<?> collection, Collection<?> elements) {
    return collection != null && elements != null && collection.containsAll(elements);
  }

  /**
   * Gets the first element of a list, returning null if empty.
   *
   * @param list the list
   * @return the first element or null if empty
   */
  public static <T> T getFirst(List<T> list) {
    return isEmpty(list) ? null : list.get(0);
  }

  /**
   * Gets the last element of a list, returning null if empty.
   *
   * @param list the list
   * @return the last element or null if empty
   */
  public static <T> T getLast(List<T> list) {
    return isEmpty(list) ? null : list.get(list.size() - 1);
  }

  /**
   * Checks if a collection has a specific size.
   *
   * @param collection the collection to check
   * @param size the expected size
   * @return true if the collection has the specified size, false otherwise
   */
  public static boolean hasSize(Collection<?> collection, int size) {
    return collection != null && collection.size() == size;
  }

  /**
   * Checks if a collection has at least one element.
   *
   * @param collection the collection to check
   * @return true if the collection has at least one element, false otherwise
   */
  public static boolean hasElements(Collection<?> collection) {
    return isNotEmpty(collection);
  }

  /**
   * Checks if a collection has only one element.
   *
   * @param collection the collection to check
   * @return true if the collection has exactly one element, false otherwise
   */
  public static boolean hasSingleElement(Collection<?> collection) {
    return hasSize(collection, 1);
  }

  /**
   * Checks if two collections are equal (same elements, order doesn't matter).
   *
   * @param collection1 the first collection
   * @param collection2 the second collection
   * @return true if the collections are equal, false otherwise
   */
  public static boolean areEqual(Collection<?> collection1, Collection<?> collection2) {
    if (isEmpty(collection1) && isEmpty(collection2)) {
      return true;
    }
    if (isEmpty(collection1) || isEmpty(collection2)) {
      return false;
    }
    return collection1.size() == collection2.size() && collection1.containsAll(collection2);
  }
}
