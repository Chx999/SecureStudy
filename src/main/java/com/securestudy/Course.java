package com.securestudy;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.Comparator;

public class Course {
  private String code;
  private String name;
  private List<Exam> exams;
  private Set<String> tags;

  public Course(String code, String name) {
    if (code == null || code.isBlank()) {
      throw new IllegalArgumentException("Course code cannot be blank");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Course name cannot be blank");
    }

    this.code = code;
    this.name = name;
    this.exams = new ArrayList<>();
    this.tags = new HashSet<>();
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public void addExam(Exam exam) {
    if (exam == null) {
      throw new IllegalArgumentException("Exam cannot be null");
    }

    this.exams.add(exam);
  }

  public int getExamCount() {
    return exams.size();
  }

  public void printSummary() {
    System.out.print(getSummary());
  }

  public boolean hasExam(String examName) {
    for (Exam exam : exams) {
      if (exam.getName().equals(examName)) {
        return true;
      }
    }
    return false;
  }

  public boolean addTag(String tag) {
    if (tag == null || tag.isBlank()) {
      throw new IllegalArgumentException("Course tag cannot be blank");
    }
    return this.tags.add(tag);
  }

  public boolean hasTag(String tag) {
    return tags.contains(tag);
  }

  public int getTagCount() {
    return tags.size();
  }

  public List<Exam> getCompletedExams() {
    return this.exams.stream()
        .filter(exam -> exam.getStatus() == ExamStatus.COMPLETED)
        .toList();
  }

  public List<Exam> getExamsOnOrAfter(LocalDate fromDate) {
    if (fromDate == null) {
      throw new IllegalArgumentException("Start date cannot be null");
    }
    return this.exams.stream()
        .filter(exam -> !exam.getDate().isBefore(fromDate))
        .sorted(Comparator.comparing(exam -> exam.getDate()))
        .toList();
  }

  public String getSummary() {
    StringBuilder summary = new StringBuilder();
    summary.append(this.code)
        .append(": ")
        .append(this.name)
        .append(System.lineSeparator());

    for (Exam exam : this.exams) {
      summary.append("- ")
          .append(exam.getName())
          .append(" (")
          .append(exam.getDate())
          .append(") [")
          .append(exam.getStatus())
          .append("]")
          .append(System.lineSeparator());
    }

    return summary.toString();
  }

}
