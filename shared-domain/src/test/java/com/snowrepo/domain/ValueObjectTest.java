package com.snowrepo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValueObject Tests")
class ValueObjectTest {

  @Test
  @DisplayName("Should be equal by attributes")
  void testEqualityByAttributes() {
    TestValueObject vo1 = new TestValueObject("test", 123);
    TestValueObject vo2 = new TestValueObject("test", 123);

    assertEquals(vo1, vo2);
    assertEquals(vo1.hashCode(), vo2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal with different attributes")
  void testInequalityWithDifferentAttributes() {
    TestValueObject vo1 = new TestValueObject("test1", 123);
    TestValueObject vo2 = new TestValueObject("test2", 456);

    assertNotEquals(vo1, vo2);
    assertNotEquals(vo1.hashCode(), vo2.hashCode());
  }

  @Test
  @DisplayName("Should have consistent hash code")
  void testHashCodeConsistency() {
    TestValueObject vo = new TestValueObject("test", 123);

    int hashCode1 = vo.hashCode();
    int hashCode2 = vo.hashCode();

    assertEquals(hashCode1, hashCode2);
  }

  @Test
  @DisplayName("Should have meaningful string representation")
  void testToString() {
    TestValueObject vo = new TestValueObject("test", 123);

    String toString = vo.toString();

    assertNotNull(toString);
    assertFalse(toString.isEmpty());
  }

  /**
   * Test implementation of ValueObject for testing purposes.
   */
  static class TestValueObject extends ValueObject {

    private final String name;
    private final int value;

    TestValueObject(String name, int value) {
      this.name = name;
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof TestValueObject)) {
        return false;
      }
      TestValueObject that = (TestValueObject) o;
      return value == that.value && name.equals(that.name);
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + value;
      return result;
    }

    @Override
    public String toString() {
      return "TestValueObject{" + "name='" + name + '\'' + ", value=" + value + '}';
    }
  }
}
