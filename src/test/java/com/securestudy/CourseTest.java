package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseTest {
  @Test
  void countsAddedExams() {
    // Arrange
    Course course = new Course("Algebra");

    // Act
    course.addExam("Algebra I");
    course.addExam("Algebra II");

    // Assert
    assertEquals(2, course.getExamCount());
  }

  @Test
  void rejectsBlankExamName() {
    // Arrange
    Course course = new Course("Algebra");

    // Act
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class, () -> course.addExam(""));

    // Assert
    assertEquals("The exam name can not be blank", exception.getMessage());
  }

  @Test
  void rejectsNullExamName() {
    // Arrange
    Course course = new Course("Algebra");

    // Act
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class, () -> course.addExam(null));

    // Assert
    assertEquals("The exam name can not be blank", exception.getMessage());
    assertEquals(0, course.getExamCount());
  }

}
