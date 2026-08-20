package com.securestudy;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CourseTest {

  @Test
  void countsAddedExams() {
    // Arrange
    Course course = new Course("Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));

    Exam algebraTwo = new Exam(
        "Algebra II",
        LocalDate.of(2026, 10, 20));

    // Act
    course.addExam(algebraOne);
    course.addExam(algebraTwo);

    // Assert
    assertEquals(2, course.getExamCount());
  }

  @Test
  void rejectsNullExam() {
    // Arrange
    Course course = new Course("Algebra");

    // Act
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class, () -> course.addExam(null));

    // Assert
    assertEquals("Exam cannot be null", exception.getMessage());
    assertEquals(0, course.getExamCount());
  }

  @Test
  void findsExamByName() {
    // Arrange
    Course course = new Course("Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));
    course.addExam(algebraOne);

    // Act and assert
    assertTrue(course.hasExam("Algebra I"));
    assertFalse(course.hasExam("Algebra III"));
  }

}
