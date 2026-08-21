package com.securestudy;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

class LambdaTest {
  @Test
  void identifiesCompletedExam() {
    Predicate<Exam> isCompleted = exam -> exam.getStatus() == ExamStatus.COMPLETED;
    Exam exam = new Exam("Algebra", LocalDate.of(2010, 10, 10));
    assertFalse(isCompleted.test(exam));

    exam.markAsCompleted();

    assertTrue(isCompleted.test(exam));
  }
}
