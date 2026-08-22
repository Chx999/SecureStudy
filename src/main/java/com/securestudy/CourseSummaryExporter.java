package com.securestudy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CourseSummaryExporter {

  public void export(Course course, Path file) throws IOException {
    if (course == null) {
      throw new IllegalArgumentException("Course cannot be null");
    }

    if (file == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }

    Files.writeString(file, course.getSummary());
  }
}
