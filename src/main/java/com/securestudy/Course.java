package com.securestudy;

import java.util.List;
import java.util.ArrayList;

public class Course {
  private String name;
  private List<String> exams;

  public Course(String name) {
    this.name = name;
    this.exams = new ArrayList<String>();
  }

  public void addExam(String examName) throws IllegalArgumentException {
    if (examName == null || examName.isBlank())
      throw new IllegalArgumentException("The exam name can not be blank");
    else
      this.exams.add(examName);
  }

  public int getExamCount() {
    return exams.size();
  }

  public void printSummary() {
    System.out.print("The course is: " + this.name + "\n");
    System.out.print("This course contains these exams: ");
    for (String exam : this.exams) {
      System.out.print(exam + " ");
    }
    System.out.println();
  }

  public boolean hasExam(String examName) {
    return this.exams.contains(examName);
  }

}
