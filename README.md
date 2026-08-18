# SecureStudy

SecureStudy starts as a small Java exercise and will grow into a Spring Boot application.

## Week 1: Java Objects and Collections

The first exercise models one course before introducing frameworks or a database.

### Task

Create `src/main/java/com/securestudy/Course.java` with:

- private fields `name` (`String`) and `exams` (`List<String>`);
- a constructor that receives `name` and creates an empty exam list;
- `addExam(String examName)`, rejecting blank names with `IllegalArgumentException`;
- `getExamCount()`, returning the number of exams;
- `printSummary()`, printing the course name and every exam.

Then update `Main.java` to:

1. Create one `Course` object.
2. Add two exams.
3. Print its summary and exam count.
4. Try adding a blank exam name and observe the exception.

### Run

```bash
mkdir -p out
javac -d out src/main/java/com/securestudy/*.java
java -cp out com.securestudy.Main
```

### Explain Afterward

- Why is `exams` private?
  exams is private to protect the internal state of the course. Other classes
must use addExam(), so invalid data can be rejected.
- What is the difference between a `Course` class and a `Course` object?
- Why is invalid input rejected before it enters the list?
