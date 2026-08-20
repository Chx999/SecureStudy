# SecureStudy 学习复习笔记

更新日期：2026-08-19

这份笔记只记录已经实际学习和练习过的内容。当前尚未开始 Spring Boot、SQL 和 PostgreSQL。

## 1. 当前项目

SecureStudy 目前是一个普通 Java 命令行程序，用于练习课程和考试的基本建模。

```text
SecureStudy/
├── pom.xml
├── src/main/java/com/securestudy/
│   ├── Course.java
│   └── Main.java
└── target/                 # Maven 生成，不提交到 Git
```

当前功能：

- 创建课程对象；
- 添加考试名称；
- 拒绝 `null` 或空白考试名称；
- 统计考试数量；
- 打印课程摘要；
- 查找考试是否存在。

## 2. Java 基础

### 2.1 类和对象

类（class）定义一类事物的数据和行为，可以理解为设计图。

```java
public class Course {
    // fields and methods
}
```

对象（object / instance）是根据类创建的具体实例。

```java
Course algebra = new Course("algebra");
```

`Course` 是类型，`algebra` 是变量名，`new Course(...)` 创建对象。

### 2.2 字段、封装和 private

`Course` 使用字段保存对象状态：

```java
private String name;
private List<String> exams;
```

`private` 阻止其他类直接修改内部状态。外部代码必须通过 `addExam()` 操作考试列表，因此 `Course` 可以在保存数据前进行验证。

这种由对象控制内部数据的做法叫封装（encapsulation）。

### 2.3 构造器和 this

构造器（constructor）在创建对象时初始化对象。

```java
public Course(String name) {
    this.name = name;
    this.exams = new ArrayList<>();
}
```

- `this.name` 表示当前对象的字段；
- `name` 表示构造器参数；
- `new ArrayList<>()` 真正创建一个空列表；
- 如果没有创建列表，字段默认是 `null`，调用 `add()` 会产生 `NullPointerException`。

每个 `Course` 对象都有自己的考试列表。

### 2.4 List 和 ArrayList

```java
private List<String> exams;
this.exams = new ArrayList<>();
```

- `List` 是接口（interface），描述列表应支持的操作；
- `ArrayList` 是具体实现（implementation）；
- 字段使用接口类型，可以减少代码对具体实现的依赖；
- `String` 是列表中元素的类型。

已使用的方法：

```java
exams.add(examName);
exams.size();
exams.contains(examName);
```

### 2.5 输入验证和异常

当前验证逻辑：

```java
if (examName == null || examName.isBlank()) {
    throw new IllegalArgumentException("The exam name cannot be blank");
}
```

为什么先验证：防止无效数据进入列表，保持数据完整性（data integrity）。

`||` 具有短路特性（short-circuit evaluation）。如果 `examName == null` 已经为 `true`，Java 不会执行右侧的 `examName.isBlank()`，因此避免 `NullPointerException`。

异常相关词汇：

- `throw`：实际抛出一个异常；
- `throws`：在方法签名中声明可能抛出的异常；
- `try`：执行可能失败的代码；
- `catch`：捕获并处理匹配的异常。

```java
try {
    algebra.addExam("");
} catch (IllegalArgumentException exception) {
    System.out.println(exception.getMessage());
}
```

`IllegalArgumentException` 是运行时异常（unchecked exception），方法签名不强制写 `throws IllegalArgumentException`。

### 2.6 查找和时间复杂度

```java
public boolean hasExam(String examName) {
    for (Exam exam : exams) {
        if (exam.getName().equals(examName)) {
            return true;
        }
    }
    return false;
}
```

当前查找从第一个 `Exam` 开始依次比较名称：

- 第一个元素匹配：比较 1 次；
- 最后一个元素匹配：比较 `n` 次；
- 元素不存在：比较 `n` 次；
- 最坏时间复杂度：`O(n)`。

### 2.7 对象组合

最初的 `Course` 只保存考试名称：

```java
private List<String> exams;
```

当考试需要同时保存名称、日期和状态时，字符串不再足够，因此创建 `Exam` 类并改为：

```java
private List<Exam> exams;
```

一个对象包含其他对象叫对象组合（composition）。当前职责划分：

- `Exam` 负责保证自己的名称和日期有效；
- `Course` 负责管理 `Exam` 集合；
- `Course.addExam()` 只需要拒绝 `null`，不重复验证 `Exam` 内部字段。

泛型提供类型安全。`List<Exam>` 可以接收 `Exam`，不能接收 `String`。

### 2.8 LocalDate

`LocalDate` 表示不带具体时间和时区的日期，适合考试日期：

```java
LocalDate date = LocalDate.of(2026, 10, 15);
```

`LocalDate` 不是字符串。只要对象不是 `null`，它就代表一个有效日期，因此不需要调用 `toString().isBlank()`。

### 2.9 enum

`enum` 表示有限且固定的一组值：

```java
public enum ExamStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}
```

- `ExamStatus` 是类型；
- `SCHEDULED` 是该类型的一个值；
- enum 防止 `"completd"` 等无效字符串状态；
- 新 `Exam` 默认使用 `ExamStatus.SCHEDULED`；
- `markAsCompleted()` 表达业务行为，比允许任意值的通用 setter 更明确。

## 3. Package、编译和运行

### 3.1 Package

```java
package com.securestudy;
```

package 用于组织类并避免类名冲突。它和目录结构对应：

```text
com.securestudy.Main
        ↓
com/securestudy/Main.class
```

### 3.2 main 方法

```java
public static void main(String[] args)
```

这是普通 Java 程序的入口。JVM 从这里开始执行程序。

### 3.3 手动编译

```bash
mkdir -p out
javac -d out src/main/java/com/securestudy/*.java
```

- `javac` 编译 `.java` 源文件；
- `-d out` 指定 `.class` 文件的输出根目录。

运行：

```bash
java -cp out com.securestudy.Main
```

### 3.4 运行 Maven 编译结果

```bash
java -cp target/classes com.securestudy.Main
```

- `java` 启动 JVM；
- `-cp` 是 `--class-path` 的简写；
- `target/classes` 是 JVM 查找类的根目录；
- `com.securestudy.Main` 是完全限定类名（fully qualified class name）；
- 传给 `java` 的是类名，不写 `.class`。

JVM 最终查找：

```text
target/classes/com/securestudy/Main.class
```

## 4. Git 基础

### 4.1 三个重要区域

```text
工作区（working tree）
        ↓ git add
暂存区（staging area）
        ↓ git commit
提交历史（repository history）
```

- 工作区：当前看到和编辑的文件；
- 暂存区：准备放入下一次提交的内容；
- commit：保存的版本快照（snapshot）。

### 4.2 常用命令

```bash
git status
git add <files>
git diff
git diff --cached
git commit -m "message"
git log --oneline --decorate --all --graph
```

- `git diff` 查看未暂存修改；
- `git diff --cached` 查看已经暂存、即将提交的修改；
- 提交前应检查 `git status` 和 diff。

### 4.3 修改最新提交

如果最新 commit 的信息写错，并且尚未推送到共享远程仓库，可以修改它：

```bash
git commit --amend -m "new commit message"
```

例如：

```bash
git commit --amend -m "test: separate blank and null validation"
```

`--amend` 不是直接编辑原 commit，而是用当前暂存区内容和新信息创建一个替代提交，因此 commit hash 会变化。

使用前应确认：

```bash
git status
git log -1 --oneline
```

- 适合修改最新提交的信息，或补入最新提交中遗漏的文件；
- 如果只修改信息，先确保暂存区没有意外内容；
- 对尚未推送的个人分支使用通常比较安全；
- 已推送到共享远程仓库的 commit 不应随意 amend，因为这会重写其他人可能已经使用的历史；
- `--amend` 只能直接修改最新 commit，更早的 commit 需要其他历史编辑方法，目前暂时不学习。

### 4.4 分支

创建并切换到功能分支：

```bash
git switch -c feature/find-exam
```

分支本质上是指向某个 commit 的可移动指针。刚创建分支时，`main` 和 feature 分支可以指向同一个 commit。

未提交修改属于工作区，不属于任何分支。只要不会覆盖文件，切换分支时 Git 可以保留这些修改，所以提交前可能感觉两个分支的内容一样。

### 4.5 快进合并

```bash
git switch main
git merge --ff-only feature/find-exam
```

`--ff-only` 表示只允许 fast-forward merge（快进合并）。如果 `main` 没有产生其他新提交，Git 只需要把 `main` 指针向前移动，不创建额外 merge commit。

如果分支已经分叉，`--ff-only` 会拒绝合并，避免 Git 自动创建未预期的合并提交。

合并后删除功能分支：

```bash
git branch -d feature/find-exam
```

删除已合并的分支指针不会删除 `main` 中的代码。

### 4.6 .gitignore

当前忽略：

```text
out/
target/
*.class
.idea/
*.iml
```

编译产物可以通过源代码重新生成，不应提交，否则会增加仓库体积并产生无意义的修改。

## 5. Maven 基础

### 5.1 Maven 是什么

Maven 是 Java 构建工具（build tool），用于统一处理：

```text
清理 → 下载依赖 → 编译 → 测试 → 打包
```

Maven 不是 Java 编译器。它读取配置并调用 `javac` 等工具完成实际工作。

```text
pom.xml（构建说明） ─┐
                    ├─→ Maven ─→ target/（构建结果）
Java 源代码 ────────┘
```

### 5.2 pom.xml

`pom.xml` 是 Maven 的项目配置文件。POM 表示 Project Object Model。

当前重要配置：

```xml
<groupId>com.securestudy</groupId>
<artifactId>securestudy</artifactId>
<version>0.1.0-SNAPSHOT</version>
```

- `groupId`：项目所属组织或命名空间；
- `artifactId`：项目名称；
- `version`：项目版本；
- `SNAPSHOT`：仍在开发、可能继续变化的版本。

```xml
<maven.compiler.release>21</maven.compiler.release>
```

当前使用 JDK 26 执行 Maven，但编译结果以 Java 21 为目标版本。

### 5.3 标准目录

```text
src/main/java       正式 Java 代码
src/main/resources  正式资源和配置
src/test/java       测试代码
target/classes      正式代码编译结果
target/test-classes 测试代码编译结果
```

Maven 遵循约定优于配置（convention over configuration），因此认识这些标准目录。

### 5.4 Maven 生命周期命令

```bash
mvn clean
```

删除旧的 `target/`。

```bash
mvn compile
```

编译 `src/main/java`，结果进入 `target/classes`。

```bash
mvn test
```

编译并运行测试。当前尚未添加测试。

```bash
mvn package
```

依次完成前面的必要阶段，然后在 `target/` 中生成 JAR。

生命周期具有顺序：

```text
compile → test → package
```

### 5.5 依赖和仓库

依赖（dependency）是项目使用的第三方库。Maven 已经根据 `pom.xml` 下载 JUnit，以后也会用相同方式管理 Spring Boot 等依赖。

- Maven Central：常用公共远程仓库；
- `~/.m2/repository/`：本机 Maven 仓库；
- 首次构建出现大量 `Downloading` 通常是正常现象；
- 已下载文件通常会被后续构建复用。

## 6. JUnit 基础

### 6.1 JUnit 解决什么问题

人工打印结果需要人观察和判断，JUnit 可以自动比较实际结果与预期结果。测试失败时，Maven 构建也会失败，从而阻止错误代码被当作正常结果交付。

测试代码位于：

```text
src/test/java/com/securestudy/
```

Maven 将测试编译到：

```text
target/test-classes/
```

JUnit 依赖使用：

```xml
<scope>test</scope>
```

这表示依赖只用于测试的编译和运行，不进入正式应用代码。

### 6.2 测试方法和 AAA

`@Test` 告诉 JUnit 该方法是测试方法。测试不需要 `main()`，Maven 和 JUnit 会负责发现并执行它。

```java
@Test
void countsAddedExams() {
    // Arrange: 准备对象和数据
    Course course = new Course("Algebra");

    // Act: 执行要测试的行为
    course.addExam("Algebra I");
    course.addExam("Algebra II");

    // Assert: 验证实际结果
    assertEquals(2, course.getExamCount());
}
```

AAA 表示：

- Arrange：准备测试条件；
- Act：执行目标行为；
- Assert：验证结果。

### 6.3 assertEquals

基本格式：

```java
assertEquals(expected, actual);
```

- `expected` 是预期结果；
- `actual` 是程序产生的实际结果；
- 两者不相等时测试失败。

### 6.4 assertThrows

`assertThrows` 验证一段代码是否抛出预期异常：

```java
IllegalArgumentException exception = assertThrows(
    IllegalArgumentException.class,
    () -> course.addExam("")
);
```

- `IllegalArgumentException.class` 是预期异常类型；
- `() -> course.addExam("")` 是交给 JUnit 执行的无参数 Lambda；
- `assertThrows` 返回捕获到的异常对象；
- `exception.getMessage()` 可以取得并验证异常信息。

```java
assertEquals("The exam name can not be blank", exception.getMessage());
```

### 6.5 阅读测试结果

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

- `Tests run`：执行的测试数量；
- `Failures`：测试执行完成，但断言不符合预期；
- `Errors`：测试执行中出现未正确处理的问题；
- `Skipped`：被跳过的测试。

测试显示绿色只代表现有断言通过，不代表需求覆盖完整。测试名称、输入场景和断言必须一致。例如，空字符串和 `null` 应分别测试，否则可能在修改测试时意外丢失一个场景。

每个测试应独立准备自己需要的数据。避免同时使用同名类字段和局部变量，否则会产生变量遮蔽（variable shadowing），让测试实际操作的对象难以判断。

运行所有测试：

```bash
mvn clean test
```

## 7. 已遇到的错误及根因

### Git 无法运行 less

```text
cannot run less: No such file or directory
unable to execute pager 'less'
```

根因：Git 想使用 `less` 分页显示 diff，但系统没有安装它。

Arch Linux 修复：

```bash
sudo pacman -S less
```

临时绕过分页器：

```bash
git --no-pager diff
```

### main 和 feature 看起来内容相同

根因：修改还没有 commit，仍在共享的工作区中。提交到 feature 分支后，两个分支指针才会指向不同 commit。

### 已删除分支无法再次合并

如果功能已合并并删除，再次执行：

```bash
git merge --ff-only feature/find-exam
```

Git 会找不到该分支。这不是 `--ff-only` 参数错误，而是该操作已经完成。

## 8. 技术英语

| English | 中文 |
| --- | --- |
| class | 类 |
| object / instance | 对象 / 实例 |
| field | 字段 |
| constructor | 构造器 |
| encapsulation | 封装 |
| interface | 接口 |
| implementation | 实现 |
| validation | 验证 |
| exception | 异常 |
| build tool | 构建工具 |
| dependency | 依赖 |
| classpath | 类路径 |
| working tree | 工作区 |
| staging area | 暂存区 |
| commit | 提交 / 版本快照 |
| branch | 分支 |
| merge | 合并 |
| fast-forward | 快进 |
| time complexity | 时间复杂度 |

项目介绍修正版：

> Today I created my first personal project, SecureStudy, a system that will help students organise their exams. The Course class contains a String field that stores the course name. I used a feature branch because I added a new method to the Course class.

西班牙语介绍：

> Mi proyecto se llama SecureStudy. Su objetivo es desarrollar un sistema que ayude a los estudiantes a organizar sus exámenes y prepararse para ellos.

## 9. 当前掌握层级

### 必须掌握

- 类和对象的区别；
- `private` 和封装的目的；
- 构造器为什么需要初始化列表；
- `List` 与 `ArrayList` 的基本关系；
- `List<Exam>` 的泛型类型安全；
- composition、`LocalDate` 和 enum 的用途；
- `throw`、`try` 和 `catch`；
- package、classpath 和完全限定类名；
- Git 工作区、暂存区和 commit；
- 分支是指向 commit 的指针；
- Maven 读取 `pom.xml` 并管理构建；
- `clean`、`compile`、`test`、`package` 的基本作用；
- JUnit `@Test`、AAA、`assertEquals` 和 `assertThrows`。

### 了解即可

- unchecked exception；
- `ArrayList.contains()` 的 `O(n)` 复杂度；
- Maven Central 和本地仓库；
- fast-forward merge；
- Java 21 编译目标与当前 JDK 26 的区别。

### 尚未学习

- Maven 复杂插件配置；
- Spring Boot；
- SQL 和 PostgreSQL；
- REST API；
- Docker 和 CI/CD。

## 10. 自测问题

1. `Course` 类和 `algebra` 对象有什么区别？
2. 为什么 `exams` 应该是 `private`？
3. 为什么列表要在构造器中创建？
4. `throw` 和 `throws` 有什么区别？
5. 为什么判断 `null` 必须放在 `isBlank()` 前面？
6. `List` 和 `ArrayList` 分别是什么？
7. `ArrayList.contains()` 的最坏时间复杂度是什么？
8. `java -cp target/classes com.securestudy.Main` 的每部分是什么意思？
9. 未提交修改为什么可能在切换分支后继续存在？
10. `git diff` 和 `git diff --cached` 有什么区别？
11. `--ff-only` 限制了什么？
12. Maven 和 `javac` 的职责有什么区别？
13. `pom.xml` 解决什么问题？
14. 为什么不应提交 `target/`？
15. `mvn package` 为什么也会执行编译和测试阶段？
16. JUnit 测试为什么不需要 `main()`？
17. Arrange、Act、Assert 分别负责什么？
18. `assertEquals(expected, actual)` 中两个参数的顺序是什么？
19. `assertThrows` 为什么需要接收 Lambda？
20. 为什么测试全部通过仍不一定代表需求覆盖完整？
21. 为什么 `Course` 应保存 `Exam`，而不是继续保存多个字符串？
22. `Exam` 和 `Course` 分别负责验证什么？
23. 为什么 `LocalDate` 只需要检查 `null`，不需要检查 blank？
24. enum 相比字符串状态有什么优势？
25. 为什么每个测试应独立准备数据？
