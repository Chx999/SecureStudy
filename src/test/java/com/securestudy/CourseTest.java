package com.securestudy;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class CourseTest {

  @Test
  void countsAddedExams() {
    // Arrange
    Course course = new Course("ALG101", "Algebra");
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
    Course course = new Course("ALG101", "Algebra");

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
    Course course = new Course("ALG101", "Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));
    course.addExam(algebraOne);

    // Act and assert
    assertTrue(course.hasExam("Algebra I"));
    assertFalse(course.hasExam("Algebra III"));
  }

  @Test
  void storesCodeAndName() {
    // Arrange and act
    String code = "ALG101";
    String name = "Algebra";

    // Act
    Course course = new Course(code, name);

    // Assert
    assertEquals(code, course.getCode());
    assertEquals(name, course.getName());

  }

  @Test
  void rejectsBlankCode() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Course("   ", "Algebra"));

    assertEquals("Course code cannot be blank", exception.getMessage());
  }

  @Test
  void rejectsBlankName() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Course("ALG101", "   "));

    assertEquals("Course name cannot be blank", exception.getMessage());
  }

  @Test
  void addsTag() {
    Course course = new Course("ALG101", "Algebra");

    assertTrue(course.addTag("math"));
    assertTrue(course.hasTag("math"));
    assertFalse(course.hasTag("required"));
    assertEquals(1, course.getTagCount());
  }

  @Test
  void ignoresDuplicateTag() {
    Course course = new Course("ALG101", "Algebra");

    course.addTag("math");
    assertFalse(course.addTag("math"));
    assertEquals(1, course.getTagCount());
  }

  @Test
  void rejectsBlankTag() {
    Course course = new Course("ALG101", "Algebra");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> course.addTag("  "));

    assertEquals("Course tag cannot be blank", exception.getMessage());
  }

  @Test
  void rejectsNullTag() {
    Course course = new Course("ALG101", "Algebra");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> course.addTag(null));

    assertEquals("Course tag cannot be blank", exception.getMessage());
  }

  @Test
  void returnsOnlyCompletedExams() {
    Course course = new Course("ALG101", "Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));
    Exam algebraTwo = new Exam(
        "Algebra II",
        LocalDate.of(2026, 10, 20));
    algebraOne.markAsCompleted();

    course.addExam(algebraOne);
    course.addExam(algebraTwo);

    List<Exam> completedExams = course.getCompletedExams();
    assertEquals(1, completedExams.size());
    assertSame(algebraOne, completedExams.get(0));
    assertEquals(2, course.getExamCount());
  }

  @Test
  void returnsExamsOnOrAfterDateInDateOrder() {
    Course course = new Course("ALG101", "Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 1));
    Exam algebraTwo = new Exam(
        "Algebra II",
        LocalDate.of(2026, 10, 15));
    Exam algebraThree = new Exam(
        "Algebra III",
        LocalDate.of(2026, 10, 20));
    course.addExam(algebraThree);
    course.addExam(algebraOne);
    course.addExam(algebraTwo);

    LocalDate fromDate = LocalDate.of(2026, 10, 15);
    List<Exam> examsOnOrAfter = course.getExamsOnOrAfter(fromDate);

    assertEquals(2, examsOnOrAfter.size());
    assertSame(algebraTwo, examsOnOrAfter.get(0));
    assertSame(algebraThree, examsOnOrAfter.get(1));
    assertFalse(examsOnOrAfter.contains(algebraOne));
    assertEquals(3, course.getExamCount());

  }

  @Test
  void rejectsNullStartDate() {
    Course course = new Course("ALG101", "Algebra");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> course.getExamsOnOrAfter(null));

    assertEquals("Start date cannot be null", exception.getMessage());
  }

}
