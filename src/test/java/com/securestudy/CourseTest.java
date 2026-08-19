package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
