package com.securestudy;

import java.time.LocalDate;

public class Exam {
  private String name;
  private LocalDate date;
  private ExamStatus status;

  public Exam(String name, LocalDate date) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Exam name can not be blank");
    }
    if (date == null) {
      throw new IllegalArgumentException("Exam date can not be null");
    }

    this.name = name;
    this.date = date;
    this.status = ExamStatus.SCHEDULED;
  }

  // Getters and setters
  public String getName() {
    return this.name;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public ExamStatus getStatus() {
    return this.status;
  }

  public void markAsCompleted() {
    this.status = ExamStatus.COMPLETED;
  }

}
