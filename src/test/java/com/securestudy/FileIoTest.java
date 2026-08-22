package com.securestudy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileIoTest {
  @TempDir
  Path tempDir;

  @Test
  void writesAndReadsText() throws IOException {
    Path file = tempDir.resolve("course-summary.txt");
    String expected = "ALG101: Algebra";

    Files.writeString(file, expected);
    String actual = Files.readString(file);

    assertEquals(expected, actual);
  }
}
