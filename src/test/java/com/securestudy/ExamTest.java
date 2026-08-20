package com.securestudy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

class ExamTest {
  @Test
  void storesNameAndDate() {
    // Arrange
    LocalDate date = LocalDate.of(2026, 10, 15);
    Exam exam = new Exam("Algebra I", date);

    // Act
    String examName = exam.getName();
    LocalDate examDate = exam.getDate();

    // Assertions
    assertEquals("Algebra I", examName);
    assertEquals(date, examDate);
  }

  @Test
  void rejectsBlankName() {
    assertThrows(IllegalArgumentException.class,
        () -> new Exam("    ", LocalDate.of(2010, 10, 10)));
  }

  @Test
  void rejectsNullName() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Exam(null, LocalDate.of(2010, 10, 10)));
  }

  @Test
  void rejectsNullDate() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Exam("Algebra I", null));
  }

  @Test
  void startsAsScheduled() {
    // Arrange and act
    Exam exam = new Exam("Algebral", LocalDate.of(2010, 10, 10));

    // Assert
    assertEquals(ExamStatus.SCHEDULED, exam.getStatus());
  }

  @Test
  void canBeMarkAsCompleted() {
    // Arrange
    Exam exam = new Exam("Algebral", LocalDate.of(2010, 10, 10));

    // Act
    exam.markAsCompleted();

    // Assert
    assertEquals(ExamStatus.COMPLETED, exam.getStatus());
  }

}
