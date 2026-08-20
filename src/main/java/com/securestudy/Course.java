package com.securestudy;

import java.util.List;
import java.util.ArrayList;

public class Course {
  private String name;
  private List<Exam> exams;

  public Course(String name) {
    this.name = name;
    this.exams = new ArrayList<>();
  }

  public void addExam(Exam exam) {
    if (exam == null)
      throw new IllegalArgumentException("Exam cannot be null");

    this.exams.add(exam);
  }

  public int getExamCount() {
    return exams.size();
  }

  public void printSummary() {
    for (Exam exam : exams) {
      System.out.println(exam.getName() +
          " (" + exam.getDate() + ")");
    }
  }

  public boolean hasExam(String examName) {
    for (Exam exam : exams) {
      if (exam.getName().equals(examName)) {
        return true;
      }
    }
    return false;
  }

}
