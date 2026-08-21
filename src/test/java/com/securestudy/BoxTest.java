package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BoxTest {
  @Test
  void storesString() {
    Box<String> box = new Box<>("hello");
    assertEquals("hello", box.getValue());
  }

  @Test
  void storesCourse() {
    Course course = new Course("ALG101", "Algebra");
    Box<Course> box = new Box<>(course);
    assertSame(course, box.getValue());
  }
}
