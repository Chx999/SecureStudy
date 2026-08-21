package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourseCatalogTest {
  @Test
  void addsAndFindsCourse() {
    // Arrange
    Course course = new Course("ALG101", "Algebra");
    CourseCatalog courses = new CourseCatalog();

    // Act
    courses.addCourse(course);

    // Assert
    assertSame(course, courses.findCourseByCode("ALG101"));

  }

  @Test
  void rejectsDuplicateCourseCode() {
    // Arrange
    CourseCatalog courses = new CourseCatalog();
    Course course1 = new Course("ALG101", "Algebra");
    Course course2 = new Course("ALG101", "English");

    // Act
    courses.addCourse(course1);

    // Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> courses.addCourse(course2));

    assertEquals(
        "Course code already exists: ALG101",
        exception.getMessage());

    assertEquals(1, courses.getCourseCount());
    assertSame(course1, courses.findCourseByCode("ALG101"));
  }

  @Test
  void returnsNullForUnknownCourse() {
    CourseCatalog courses = new CourseCatalog();
    Course course1 = new Course("ALG101", "Algebra");
    courses.addCourse(course1);

    assertNull(courses.findCourseByCode("ALG102"));

  }

  @Test
  void rejectsNullCourse() {
    CourseCatalog courses = new CourseCatalog();

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> courses.addCourse(null));

    assertEquals("Course cannot be null", exception.getMessage());
    assertEquals(0, courses.getCourseCount());

  }

  @Test
  void reportsWhetherCourseExists() {
    Course course1 = new Course("ALG101", "Algebra");
    CourseCatalog courses = new CourseCatalog();

    courses.addCourse(course1);

    assertTrue(courses.hasCourse("ALG101"));
    assertFalse(courses.hasCourse("PROG101"));

  }

}
