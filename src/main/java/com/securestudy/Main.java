package com.securestudy;

public class Main {
  public static void main(String[] args) {
    // TODO: Create a Course, add two exams, and print its summary.
    Course algebra = new Course("algebra");
    algebra.addExam("Algebra I");
    algebra.addExam("Algebra II");
    algebra.printSummary();
    System.out.println("Number of exams: " + algebra.getExamCount());

    try {
      algebra.addExam("");
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }

    System.out.println(algebra.hasExam("Algebra I"));
    System.out.println(algebra.hasExam("Algebra III"));

  }
}
