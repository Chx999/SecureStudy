package com.securestudy;

import java.util.List;

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

  @Test
  void returnsCoursesWithTagInCodeOrder() {
    CourseCatalog courses = new CourseCatalog();

    Course course1 = new Course("ALG101", "Algebra");
    course1.addTag("math");

    Course course2 = new Course("MAT201", "Calcul");
    course2.addTag("math");

    Course course3 = new Course("PRO101", "Programacion");
    course3.addTag("programming");

    courses.addCourse(course2);
    courses.addCourse(course3);
    courses.addCourse(course1);

    List<Course> coursesWithMathTag = courses.getCoursesWithTag("math");
    assertEquals("ALG101", coursesWithMathTag.get(0).getCode());
    assertEquals("MAT201", coursesWithMathTag.get(1).getCode());
    assertEquals(2, coursesWithMathTag.size());
  }

  @Test
  void rejectsBlankTagFilter() {
    CourseCatalog courses = new CourseCatalog();
    Course course1 = new Course("ALG101", "Algebra");
    course1.addTag("math");
    courses.addCourse(course1);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> courses.getCoursesWithTag("   "));

    assertEquals("Course tag cannot be blank", exception.getMessage());
  }

  @Test
  void removeExistingCourse() {
    CourseCatalog courses = new CourseCatalog();
    Course course1 = new Course("ALG101", "Algebra");
    courses.addCourse(course1);

    assertTrue(courses.removeCourse("ALG101"));
    assertEquals(0, courses.getCourseCount());
    assertNull(courses.findCourseByCode("ALG101"));
  }

  @Test
  void returnsFalseWhenCourseDoesNotExist() {
    CourseCatalog courses = new CourseCatalog();
    Course course1 = new Course("ALG101", "Algebra");
    courses.addCourse(course1);

    assertFalse(courses.removeCourse("MAT201"));
    assertEquals(1, courses.getCourseCount());
    assertSame(course1, courses.findCourseByCode("ALG101"));
  }

}
