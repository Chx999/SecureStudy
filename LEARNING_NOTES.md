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

### 2.10 List、Set 和 Map

三种集合解决不同问题：

| 类型 | 核心特点 | SecureStudy 用途 |
| --- | --- | --- |
| `List<E>` | 有顺序、允许重复、可按索引访问 | 一个课程中的考试列表 |
| `Set<E>` | 元素唯一、不通过索引访问 | 一个课程的不重复标签 |
| `Map<K, V>` | key 对应 value，key 唯一 | 课程编号对应课程对象 |

当前具体实现：

```java
private List<Exam> exams = new ArrayList<>();
private Set<String> tags = new HashSet<>();
private Map<String, Course> courses = new HashMap<>();
```

常用操作：

```java
list.add(value);
set.add(value);
set.contains(value);
map.put(key, value);
map.get(key);
map.containsKey(key);
```

`HashSet.add()` 会返回是否真正加入了新元素。第一次加入返回 `true`，重复元素返回 `false`。

`HashMap.put()` 遇到相同 key 时会替换旧 value。`CourseCatalog` 在调用 `put()` 前使用 `containsKey()` 检查课程编号，避免不同课程被无提示覆盖。

课程编号已经保存在 `Course` 中，所以 `CourseCatalog.addCourse(Course course)` 使用 `course.getCode()` 作为 key，不要求调用者重复传入编号。这遵循单一事实来源（Single Source of Truth）。

当前复杂度只需掌握平均情况：

- `ArrayList` 按名称顺序查找：`O(n)`；
- `HashSet.add()` 和 `contains()`：平均 `O(1)`；
- `HashMap.put()`、`get()` 和 `containsKey()`：平均 `O(1)`；
- `HashSet` 和 `HashMap` 不保证遍历顺序。

业务处理可以不同：重复课程编号可能代表数据冲突，因此抛出异常；重复标签通常无害，因此 `Set` 保持一个值并返回 `false`。

### 2.11 Generics

Generics（泛型）使用类型参数限制可以保存和返回的数据类型：

```java
class Box<T> {
    private T value;

    T getValue() {
        return value;
    }
}
```

`T` 是类型占位符，创建对象时确定实际类型：

```java
Box<String> textBox = new Box<>("hello");
Box<Course> courseBox = new Box<>(course);
```

泛型让编译器提前发现类型错误，并让 `getValue()` 返回正确类型，不需要手动强制转换。项目中的 `List<Exam>`、`Set<String>` 和 `Map<String, Course>` 都是泛型的实际应用。

### 2.12 Lambda 和 Predicate

Lambda 可以把一小段行为表示为值：

```java
Predicate<Exam> isCompleted =
    exam -> exam.getStatus() == ExamStatus.COMPLETED;
```

`Predicate<Exam>` 表示接收一个 `Exam` 并返回 `boolean` 的判断规则。执行规则：

```java
boolean result = isCompleted.test(exam);
```

Lambda 两侧含义：

```text
exam -> exam.getStatus() == ExamStatus.COMPLETED
参数    根据参数执行并返回结果
```

Lambda 的通用形式是：

```java
参数 -> 表达式
```

右侧表达式的结果就是 Lambda 的返回值。Lambda 不固定返回 `boolean`，它的目标类型由使用位置决定，这叫 target type（目标类型）。

`filter()` 需要 `Predicate<Course>`：

```java
.filter(course -> course.hasTag(tag))
```

这里 `course.hasTag(tag)` 返回 `boolean`，因此 Lambda 类型可以理解为：

```text
Course → boolean
```

等价的显式写法：

```java
Predicate<Course> hasRequestedTag =
    course -> course.hasTag(tag);
```

`Comparator.comparing()` 需要一个提取排序字段的 `Function<Course, String>`：

```java
Comparator.comparing(course -> course.getCode())
```

这里 `course.getCode()` 返回 `String`，因此内部 Lambda 可以理解为：

```text
Course → String
```

等价的显式写法：

```java
Function<Course, String> getCourseCode =
    course -> course.getCode();
```

`Comparator.comparing()` 使用这个 String code 创建 `Comparator<Course>`，然后 `sorted()` 使用 Comparator 对课程排序：

```text
Course
→ Lambda 提取 String code
→ Comparator.comparing 创建 Comparator<Course>
→ sorted 按 code 排序
```

三个当前常见类型：

```text
Predicate<T>    T → boolean   用于判断
Function<T, R>  T → R         用于转换或提取值
Comparator<T>   比较两个 T     用于排序
```

相同的 `参数 -> 表达式` 语法可以返回不同类型，因为右侧表达式和接收它的方法要求不同：

```java
course -> course.hasTag(tag)  // Course → boolean
course -> course.getCode()    // Course → String
exam -> exam.getDate()        // Exam → LocalDate
```

enum 常量是唯一实例，因此状态可以使用 `==` 比较。

### 2.13 Stream

Stream 用流水线方式处理集合：

```text
collection → stream → filter → sorted → toList
```

筛选已完成考试：

```java
return exams.stream()
    .filter(exam -> exam.getStatus() == ExamStatus.COMPLETED)
    .toList();
```

筛选指定日期及之后的考试，并按日期排序：

```java
return exams.stream()
    .filter(exam -> !exam.getDate().isBefore(fromDate))
    .sorted(Comparator.comparing(exam -> exam.getDate()))
    .toList();
```

- `stream()` 开始处理集合；
- `filter()` 保留 Predicate 返回 `true` 的元素；
- `sorted()` 根据 Comparator 排序；
- `Comparator.comparing()` 指定用于比较的字段；
- `toList()` 产生新的结果列表；
- 当前流水线不会修改原始 `exams` 列表。

日期筛选使用 `!date.isBefore(fromDate)`，因此边界日期本身也会被包含。测试应故意使用乱序输入，否则即使遗漏 `sorted()` 也可能通过。

Java 综合验收使用 `CourseCatalog.getCoursesWithTag()` 同时复习 Map、Lambda 和 Stream：

```java
return courses.values().stream()
    .filter(course -> course.hasTag(tag))
    .sorted(Comparator.comparing(course -> course.getCode()))
    .toList();
```

类型和职责变化：

```text
Map values                    Collection<Course>
→ stream()                    Stream<Course>
→ filter(Course → boolean)    Stream<Course>
→ sorted(Course code)         Stream<Course>
→ toList()                    List<Course>
```

返回 `List<Course>` 是因为业务要求保留按 code 排序的结果。`toList()` 是生成 List 的操作，不是选择 List 的业务原因。

假设 Catalog 有 `n` 个课程，其中 `k` 个符合标签：

- `filter()`：`O(n)`；
- `sorted()`：`O(k log k)`；
- `toList()`：`O(k)`；
- 总体：`O(n + k log k)`；
- 最坏 `k = n`，总体为 `O(n log n)`。

### 2.14 File I/O

I/O 表示 Input / Output：

- Output：程序将数据写入文件；
- Input：程序从文件读取数据。

现代 Java 可以使用 `Path` 表示路径，使用 `Files` 执行文件操作：

```java
Path file = tempDir.resolve("course-summary.txt");
Files.writeString(file, content);
String actual = Files.readString(file);
```

SecureStudy 的职责划分：

- `Course.getSummary()` 生成摘要文本；
- `CourseSummaryExporter` 负责将摘要写入指定 `Path`；
- 调用者决定文件保存位置，不在 Exporter 中写死路径。

`Course.getSummary()` 使用 `StringBuilder` 和连续 `append()` 构造文本，避免在每一步创建不必要的中间字符串。

JUnit 的 `@TempDir` 会在测试前创建临时目录并注入 `Path`，测试结束后由 JUnit 主动清理。普通导出文件不会因为 Java进程结束而自动删除。

内存和磁盘的区别：

```text
内存中的 Java 对象：进程结束后消失
写入磁盘的普通文件：进程结束后仍然存在
```

### 2.15 异常传播

文件操作可能因为路径、权限、磁盘等问题抛出 `IOException`。它是 checked exception，Java要求调用者捕获或继续声明：

```java
public void export(Course course, Path file) throws IOException {
    Files.writeString(file, course.getSummary());
}
```

当前 Exporter 不决定如何恢复，而是将 `IOException` 继续交给上层调用者。这叫异常传播（exception propagation）。

两类异常的当前区别：

- `IllegalArgumentException`：调用者传入了无效参数，是 unchecked exception；
- `IOException`：文件系统操作失败，是 checked exception。

禁止使用不做任何处理的空 `catch`：

```java
try {
    exporter.export(course, file);
} catch (IOException exception) {
    // 错误被隐藏
}
```

空 `catch` 会吞掉异常，测试可能在没有成功写入文件时仍然通过，形成假阳性（false positive）。测试方法可以声明 `throws IOException`，让 JUnit 将文件错误报告为 Error。

### 2.16 JDK、JRE 和 JVM

Java 程序的基本执行过程：

```text
.java 源代码
→ javac 编译
→ .class 字节码
→ JVM 加载和执行
→ 机器指令
→ CPU 执行
```

- JDK（Java Development Kit）：开发工具包，包含编译和运行所需工具；
- JRE（Java Runtime Environment）：概念上由 JVM、标准库和运行组件组成；
- JVM（Java Virtual Machine）：加载并执行字节码、管理内存和垃圾回收。

现代 Java 通常安装完整 JDK，不一定单独安装传统 JRE。

Maven 不是编译器或 JVM。`mvn compile` 读取 `pom.xml` 并调用 JDK 中的编译工具；`java -cp ...` 启动 JVM 并执行编译后的类。

### 2.17 Stack、Heap 和引用

当前使用简化内存模型：

```text
JVM memory
├── Stack：方法调用、栈帧、局部变量和引用
└── Heap：对象和数组
```

执行：

```java
Course course = new Course("ALG101", "Algebra");
```

可以简化理解为：局部变量 `course` 在 stack frame 中保存引用，`Course` 对象位于 heap。

```text
Stack                         Heap
main frame
└── course reference ───────→ Course object
                              ├── exams → ArrayList object
                              └── tags  → HashSet object
```

每次调用方法会创建新的 stack frame，方法返回后对应 frame 被移除。每个线程有自己的 stack，多个线程可以共享 heap 中的对象。

`null` 表示引用没有指向对象。对 `null` 调用方法会产生 `NullPointerException`。

### 2.18 Garbage Collection

GC（Garbage Collection）回收已经无法再通过任何引用访问的 heap 对象。

```java
course.addExam(exam);
exam = null;
```

此时 Exam 不能被回收，因为仍存在引用路径：

```text
course → Course object → exams list → Exam object
```

只有当对象不可达（unreachable）时，它才有资格被 GC 回收。有资格回收不代表立即回收。

基础错误区别：

- `StackOverflowError`：方法调用栈通常过深，例如无限递归；
- `OutOfMemoryError`：heap 等内存区域无法继续分配对象。

当前不学习 GC 算法、JVM参数或性能调优。

### 2.19 Process、Thread 和 Runnable

Process（进程）是正在运行的程序实例。启动 Java 程序时，Linux 创建 Java进程，进程中运行 JVM 和一个或多个线程。

Java程序启动时至少有 main thread，它从 `main()` 开始执行。每个线程有自己的 stack，多个线程可以共享 heap 中的对象：

```text
Java process
├── main thread   → Stack 1
├── worker thread → Stack 2
└── shared Heap
```

`Runnable` 描述要执行的任务，`Thread` 表示执行任务的线程：

```java
Runnable task = () -> result.append("done");
Thread worker = new Thread(task);
```

三个重要方法：

- `start()`：启动新线程，由新线程执行任务；
- `run()`：像普通方法一样在当前线程执行，不会启动新线程；
- `join()`：让当前线程等待目标线程结束。

```java
worker.start();
worker.join();
```

`join()` 可能抛出 checked exception `InterruptedException`。它只负责等待，不会让共享数据操作自动变得线程安全。

### 2.20 原子操作、Race Condition 和 synchronized

原子操作（atomic operation）表示从其他线程的角度看不可分割的操作：要么尚未发生，要么已经完整完成，其他线程不能在中间状态插入修改。

`value++` 不是原子操作，可以简化为：

```text
读取 value
计算 value + 1
写回 value
```

两个线程可能都读取相同旧值并写回相同新值，导致一次更新丢失（lost update）。最终结果取决于线程执行顺序，这叫 race condition（竞态条件）。

```text
value = 0
Thread A 读取 0
Thread B 读取 0
Thread A 写入 1
Thread B 写入 1
最终 value = 1，而不是 2
```

`synchronized` 让使用同一把锁的线程一次只有一个进入受保护的方法：

```java
synchronized void increment() {
    value++;
}
```

`value++` 内部仍有多个步骤，但其他使用同一锁的线程不能在方法执行中间进入，因此整个 `increment()` 对这些线程表现为原子执行。

当前同步计数器实验：两个线程共享同一个 counter，各执行 10,000 次 `increment()`；先启动两个线程，再分别 `join()`，最终断言结果为 20,000。

```text
start()        启动线程
join()         等待线程结束
synchronized   控制共享数据的并发访问
```

暂时不学习 `AtomicInteger`、线程池、显式锁、虚拟线程或高级 Java Memory Model。

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

### 4.7 GitHub remote 和首次 push

Git 是本地版本控制工具，GitHub 是托管 Git 仓库的远程平台。没有 GitHub 时，本地仍然可以 commit、创建分支和合并；remote 用于连接本地仓库与远程仓库。

当前远程仓库：

```text
https://github.com/Chx999/SecureStudy
```

查看 remote：

```bash
git remote -v
```

`origin` 是远程仓库地址的本地别名，不是特殊服务器名称。首次推送通常使用：

```bash
git push -u origin main
```

- `push`：把本地已有的 commit 上传到远程；
- `origin`：目标远程仓库；
- `main`：要推送的本地分支；
- `-u`：建立本地 `main` 与远程 `origin/main` 的跟踪关系。

建立跟踪关系后，在 `main` 上通常可以简写为：

```bash
git push
```

`push` 只上传 commit，不会上传工作区中尚未提交的修改。`origin/main` 是 Git 在本地保存的远程分支状态记录，不是另一个需要直接编辑的本地分支。

### 4.8 Pull Request

Pull Request（PR）是在 GitHub 上请求审查并合并两个分支之间的修改。它不是 `git pull` 命令。

SecureStudy 的第一个 PR：

```text
base: main
head: feature/remove-course
```

- base：接收修改的目标分支；
- head / compare：提供修改的来源分支；
- PR 展示 Conversation、Commits、Files changed 和 Checks；
- PR 创建后可以继续向 head 分支 push，新 commit 会自动加入同一个 PR；
- GitHub 只能看到已经 commit 并 push 的修改，不能看到本地工作区。

完整流程：

```text
从 main 创建功能分支
→ 编写代码和测试
→ commit
→ push 功能分支
→ 创建 PR
→ review
→ merge 到 GitHub main
→ 更新本地 main
→ 删除已完成的功能分支
```

首次推送功能分支：

```bash
git push -u origin feature/remove-course
```

创建 PR 时比较的是该功能分支相对 `main` 多出的 commit。PR #1 使用普通 merge 方式合并，因此 GitHub 创建了一个独立的 merge commit，并保留了功能 commit 的分支关系。

如果 PR 在 GitHub 网页上合并，本地 `main` 不会自动变化，通常需要：

```bash
git switch main
git pull --ff-only
```

`git pull` 从远程取得新 commit 并整合到当前本地分支；`git push` 则把本地 commit 上传到远程。`--ff-only` 可以避免 pull 时意外创建 merge commit。

远程功能分支删除后，可以清理本地保存的过期远程跟踪记录：

```bash
git fetch --prune
```

### 4.9 PR 工作流总结

1. `main` 分支保存当前稳定的代码。开发新功能时不应直接修改 `main`，而是从 `main` 创建功能分支，完成测试和审查后再通过 PR 合并。
2. `commit` 将暂存区中的修改保存到本地仓库；`push` 将本地 commit 上传到 GitHub 远程仓库；PR 请求审查并合并分支修改；`merge` 真正合并两个分支。
3. PR 合并发生在 GitHub 远程仓库中，本地 `main` 不会自动同步，因此需要使用 `git pull --ff-only` 更新本地 `main`。

当前文档 PR 的完整操作命令：

```bash
# 检查、暂存并提交文档
git diff --check
git diff
git add LEARNING_NOTES.md
git diff --cached
git commit -m "docs: summarize pull request workflow"

# 首次推送功能分支并建立跟踪关系
git push -u origin docs/pr-workflow-summary

# 创建并在浏览器中查看 PR
gh pr create \
  --base main \
  --head docs/pr-workflow-summary \
  --title "docs: summarize pull request workflow" \
  --body "Summarize the branch, commit, push, PR, merge, and pull workflow."
gh pr view --web

# 审查通过后，将 <PR编号> 替换为实际编号
gh pr merge <PR编号> --merge --delete-branch

# 同步本地 main 并清理过期的远程跟踪记录
git switch main
git pull --ff-only
git fetch --prune
git status
```

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

测试方法如果漏写 `@Test`，Java 仍可能编译成功，但 JUnit 不会执行它。除了看 `BUILD SUCCESS`，还应检查 `Running ...Test` 和 `Tests run` 数量。

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
- `List`、`Set`、`Map` 的用途和基本选择；
- 泛型类型参数、基础 Lambda、`Predicate` 和 Stream 流水线；
- File I/O、`Path`、`Files` 和 `IOException` 传播；
- JDK、JRE、JVM、stack、heap、引用和 GC 基础；
- Thread、Runnable、`start()`、`join()` 和基础 `synchronized`；
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
- race condition、lost update 和原子操作概念。

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
26. `List`、`Set`、`Map` 分别适合什么场景？
27. 为什么课程编号适合作为 `Map` 的 key？
28. 为什么 `addCourse()` 不需要再次接收 code 参数？
29. `HashMap.put()` 遇到重复 key 会发生什么？
30. 为什么重复课程编号抛异常，而重复标签只返回 `false`？
31. `Box<T>` 中的 `T` 表示什么？
32. 泛型如何帮助编译器提前发现错误？
33. `Predicate<Exam>` 接收什么并返回什么？
34. Lambda 中 `->` 左右两侧分别表示什么？
35. `filter()`、`sorted()` 和 `toList()` 分别做什么？
36. 为什么排序测试需要故意使用乱序输入？
37. 为什么 `BUILD SUCCESS` 不一定代表新测试真的执行了？
38. `Path` 和 `Files` 分别负责什么？
39. 为什么 Exporter 接收 `Path`，而不是写死输出位置？
40. `IllegalArgumentException` 和 `IOException` 当前有什么区别？
41. 为什么空 `catch` 可能产生假阳性测试？
42. JDK、JRE 和 JVM 分别是什么？
43. `.java` 如何变成 CPU 可以执行的程序？
44. Stack 和 heap 分别主要保存什么？
45. 局部变量引用和对象本身有什么区别？
46. 为什么执行 `exam = null` 后，Course 列表中的 Exam 仍然存在？
47. 对象在什么情况下才有资格被 GC 回收？
48. 普通文件为什么能在 Java进程结束后继续存在？
49. Process 和 thread 有什么区别？
50. 多个线程的 stack 和 heap 是怎样分配的？
51. Runnable 和 Thread 分别表示什么？
52. `start()` 与直接调用 `run()` 有什么区别？
53. `join()` 解决什么问题，又不能解决什么问题？
54. 什么是原子操作？
55. 为什么 `value++` 不是原子操作？
56. 什么是 race condition 和 lost update？
57. `synchronized` 如何保护共享修改？
58. 为什么 Lambda 不一定返回 `boolean`？
59. `Predicate<T>`、`Function<T, R>` 和 `Comparator<T>` 的输入输出分别是什么？
60. `getCoursesWithTag()` 的 Stream 在每一步是什么类型？
61. 为什么查询结果选择 `List<Course>`？
62. 标签筛选和排序的整体最坏时间复杂度是什么？
63. Git 和 GitHub 分别负责什么？
64. `origin` 表示什么？
65. `git push -u origin main` 的每一部分表示什么？
66. 为什么 `git push` 不会上传尚未 commit 的修改？
67. PR 的 base branch 和 head branch 分别是什么？
68. 为什么创建 PR 前必须先 commit 并 push 功能分支？
69. `git pull` 和 Pull Request 有什么区别？
70. PR 在 GitHub 合并后，为什么本地 `main` 仍可能需要 pull？
