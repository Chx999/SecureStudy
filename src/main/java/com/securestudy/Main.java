package com.securestudy;

import java.time.LocalDate;

public class Main {
  public static void main(String[] args) {
    Course algebra = new Course("ALG101", "algebra");

    Exam algebraOne = new Exam(
        "Algebra I",
        LocalDate.of(2026, 10, 15));

    Exam algebraTwo = new Exam(
        "Algebra II",
        LocalDate.of(2026, 10, 20));

    algebra.addExam(algebraOne);
    algebra.addExam(algebraTwo);

    algebra.printSummary();

  }
}
