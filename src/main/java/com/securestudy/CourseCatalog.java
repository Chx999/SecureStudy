package com.securestudy;

import java.util.HashMap;
import java.util.Map;

public class CourseCatalog {
  private Map<String, Course> courses;

  public CourseCatalog() {
    this.courses = new HashMap<>();
  }

  public void addCourse(Course course) {
    if (course == null) {
      throw new IllegalArgumentException("Course cannot be null");
    }

    String code = course.getCode();
    if (courses.containsKey(code)) {
      throw new IllegalArgumentException("Course code already exists: " +
          code);
    }
    courses.put(code, course);
  }

  public Course findCourseByCode(String code) {
    return courses.get(code);
  }

  public boolean hasCourse(String code) {
    return courses.containsKey(code);
  }

  public int getCourseCount() {
    return courses.size();
  }

}
