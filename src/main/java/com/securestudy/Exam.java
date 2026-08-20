package com.securestudy;

import java.time.LocalDate;

public class Exam {
  private String name;
  private LocalDate date;

  public Exam(String name, LocalDate date) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Exam name can not be blank");
    }
    if (date == null) {
      throw new IllegalArgumentException("Exam date can not be null");
    }

    this.name = name;
    this.date = date;
  }

  public String getName() {
    return this.name;
  }

  public LocalDate getDate() {
    return this.date;
  }
}
