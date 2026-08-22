package com.securestudy;

import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseSummaryExporterTest {
  @TempDir
  Path tempDir;

  @Test
  void exportsCourseSummary() throws IOException {
    Path file = tempDir.resolve("course-summary.txt");
    Course course = new Course("ALG101", "Algebra");
    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));
    course.addExam(algebraOne);

    CourseSummaryExporter exporter = new CourseSummaryExporter();
    exporter.export(course, file);
    String actual = Files.readString(file);
    assertEquals(course.getSummary(), actual);

  }

  @Test
  void propagatesWriteFailure() {
    Course course = new Course("ALG101", "Algebra");
    CourseSummaryExporter exporter = new CourseSummaryExporter();

    assertThrows(
        IOException.class,
        () -> exporter.export(course, tempDir));
  }

  @Test
  void rejectsNullCourse() {
    CourseSummaryExporter exporter = new CourseSummaryExporter();
    Path file = tempDir.resolve("course-summary");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> exporter.export(null, file));

    assertEquals("Course cannot be null", exception.getMessage());
  }

  @Test
  void rejectsNullFilePath() {
    CourseSummaryExporter exporter = new CourseSummaryExporter();
    Course course = new Course("ALG101", "Algebra");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> exporter.export(course, null));

    assertEquals("File path cannot be null", exception.getMessage());
  }

}
