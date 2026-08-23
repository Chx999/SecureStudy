# SecureStudy 大二上学期 Roadmap

更新日期：2026-08-19

## 1. 目标与范围

本路线只负责大二上学期的学习与 SecureStudy V1 执行，不重新设计大三、大四或长期职业方向。

学期结束时应能够：

- 使用 Java 独立开发一个普通后端项目；
- 使用 Git、GitHub、Maven 和 JUnit；
- 使用 SQL 和 PostgreSQL 保存数据；
- 理解 HTTP、REST、IP、端口和基本网络过程；
- 使用 Spring Boot 开发 REST API；
- 完成 SecureStudy V1；
- 在 Linux 环境中运行和排查应用；
- 解释请求从客户端到 Spring Boot、PostgreSQL再返回的过程。

SecureStudy V1 范围：

- 用户；
- 课程；
- 考试；
- 复习任务；
- Spring Boot REST API；
- PostgreSQL；
- 参数验证；
- 统一异常处理；
- 日志；
- JUnit 基本测试；
- OpenAPI / Swagger；
- GitHub 仓库；
- Linux 部署。

## 2. 当前起点

已经实际练习：

- Java class、object、constructor 和封装；
- `List`、`ArrayList` 和 `contains()`；
- `IllegalArgumentException`、`try-catch` 和输入验证；
- Git commit、feature branch、fast-forward merge 和 `--amend`；
- Maven、`pom.xml`、`compile`、`test` 和 `package`；
- JUnit `@Test`、`assertEquals`、`assertThrows` 和 AAA；
- `Course` 的考试数量、空白输入和 `null` 输入测试；
- composition、`LocalDate`、`Exam` 对象和 `ExamStatus` enum；
- `List`、`Set`、`Map`、课程目录和唯一课程标签；
- Generics、`Predicate`、Lambda 和 Stream 筛选排序；
- File I/O、异常传播、JDK/JRE/JVM、stack、heap 和 GC 基础；
- GitHub remote、首次 push 和远程分支跟踪。

当前阶段：第 7 周进行中，已通过第一个 Pull Request 合并功能，下一步练习 merge conflict。

## 3. 每周负担

正常教学周课外投入约 5 至 7 小时：

- 主线学习和 SecureStudy：4 至 5 小时；
- 算法：每周 2 道基础题，约 1 小时；
- 语言：2 次英语技术阅读和 1 次西语实际交流，约 1 小时。

UB 作业或考试繁忙时缩减到 2 至 3 小时：

- 保留 1 次 60 至 90 分钟主线学习；
- 保留 1 道算法题；
- 保留 1 次英语技术阅读；
- 保留 1 次西语实际交流。

不为了追赶计划而同时开启多个新技术。

## 4. 16 周路线

| 周次     | 状态  | 核心知识                                           | SecureStudy 实践                             | 验收结果                                           |
| ------ | --- | ---------------------------------------------- | ------------------------------------------ | ---------------------------------------------- |
| 第 1 周  | 已完成 | class、object、封装、`List`、异常、Git、Maven、JUnit 入门   | `Course`、考试名称、`hasExam()` 和 3 个测试          | 能解释对象、异常、commit、classpath、Maven 和基础断言          |
| 第 2 周  | 已完成 | OOP 深化：构造器、方法、组合、`enum`                        | 创建 `Exam` 类，加入名称、日期和考试状态                   | `Course` 保存 `Exam` 对象，不再只保存字符串                 |
| 第 3 周  | 已完成 | Collections：`List`、`Set`、`Map`                 | 管理多个课程，按课程编号查找并处理重复数据                      | 能解释三种集合的用途和基本复杂度                               |
| 第 4 周  | 已完成 | Generics、Lambda、Stream 基础                      | 查询即将到来的考试，按日期排序和筛选                         | 能读懂和编写简单 Lambda，不写复杂 Stream 链                  |
| 第 5 周  | 已完成 | File I/O、异常传播、JVM 基础                           | 导出课程摘要到文本文件并读取                             | 理解 heap、stack、JDK、JRE、JVM 和文件异常                |
| 第 6 周  | 已完成 | Thread、Runnable、race condition 概念；Java 阶段复习    | 完成独立并发小练习，不加入正式业务功能                        | 了解基础线程问题，不要求掌握高级并发                             |
| 第 7 周  | 进行中 | GitHub：remote、push、pull、PR、merge conflict、tag  | 创建 GitHub 仓库，通过 PR 合并一次功能                  | 能自己解决一次人为制造的冲突                                 |
| 第 8 周  | 未开始 | PostgreSQL、SQL CRUD、主键和约束                      | 安装 PostgreSQL，手写 `users` 和 `courses` 表     | 能执行 CREATE、INSERT、SELECT、UPDATE、DELETE         |
| 第 9 周  | 未开始 | 外键、JOIN、GROUP BY、ORDER BY、ER 图                 | 设计 `users`、`courses`、`exams`、`study_tasks` | ER 图合理，能编写基础 JOIN 查询                           |
| 第 10 周 | 未开始 | Transaction、ACID、INDEX、数据库进程和连接                | 实验事务回滚，观察 PostgreSQL 进程和端口                 | 能解释 host、port、credentials 和 connection refused |
| 第 11 周 | 未开始 | IP、端口、TCP、DNS、HTTP request/response、JSON       | 设计 SecureStudy REST API                    | 能解释 HTTP 方法、状态码、Header、Body 和 JSON             |
| 第 12 周 | 未开始 | Spring Boot、Controller、Dependency Injection、配置 | 启动 Spring Boot API，实现健康检查和课程查询             | 能用 `curl` 请求 API，并解释请求如何到达 Controller          |
| 第 13 周 | 未开始 | Controller、Service、Repository、DTO              | 完成课程 CRUD，先使用简单内存实现                        | 能解释每一层职责，不把所有逻辑放进 Controller                   |
| 第 14 周 | 未开始 | JPA、PostgreSQL 连接、实体关系                         | 将用户、课程、考试和复习任务保存到 PostgreSQL               | 应用重启后数据仍存在，能排查数据库连接错误                          |
| 第 15 周 | 未开始 | Validation、统一异常处理、Logging、OpenAPI              | 加入输入校验、错误响应、日志和 Swagger                    | 错误请求返回合理状态码，API 文档可访问                          |
| 第 16 周 | 未开始 | JUnit 整合、Linux 进程、端口、日志和部署                     | 完成测试和 JAR 打包，在 Linux 上手动运行 V1              | 能部署、查看 PID/端口/日志、停止并重启程序                       |

周次是顺序参考，不是强制日历。UB 考试周可以暂停新内容并将后续阶段顺延。

## 5. 阶段关卡

### Java 基础关卡：第 1 至 6 周

- 能独立创建和组合多个类；
- 能使用集合、异常、泛型和基础 Stream；
- 能编写基础 JUnit 测试；
- 能使用 Maven 编译、测试和打包；
- 能解释 JVM 和多线程基础概念。

### 工程与数据关卡：第 7 至 11 周

- 能使用 GitHub 和 Pull Request；
- 能独立编写基础 SQL；
- 能画 SecureStudy ER 图；
- 能解释 PostgreSQL 是独立进程；
- 能解释 HTTP、IP、端口、DNS 和 JSON。

### V1 关卡：第 12 至 16 周

- Spring Boot REST API 可以运行；
- 用户、课程、考试和复习任务可以持久化；
- DTO、参数验证和统一错误响应工作正常；
- 关键业务拥有 JUnit 测试；
- Swagger 可以查看 API；
- JAR 可以在 Linux 上运行；
- 可以排查应用未启动、端口占用和数据库连接失败。

## 6. 每周循环

每个知识点按照固定顺序执行：

```text
理解一个知识点
→ 做一个独立小练习
→ 加入 SecureStudy
→ 编译和测试
→ 用自己的话解释
→ 更新 LEARNING_NOTES.md
→ Git commit
```

每周最多保留 3 类任务：

1. 学习一个核心知识点并完成小练习；
2. 将该知识点加入 SecureStudy 并测试；
3. 完成最低算法和语言练习并记录进度。

## 7. 掌握层级

### 必须掌握

- Java OOP、集合、异常和泛型；
- Git、GitHub、分支和冲突；
- Maven 和 JUnit；
- SQL、表关系和 PostgreSQL；
- HTTP、REST、IP 和端口；
- Spring Boot 基础分层；
- Validation、异常处理和日志；
- Linux 运行与基础排错。

### 了解即可

- JVM 内存基础；
- 多线程和 race condition；
- 数据库索引原理；
- TCP、DNS 和 TLS 基础；
- JPA 基本工作方式；
- Maven插件和数据库迁移概念。

### 大二上暂时不需要

- Docker 实战；
- 完整 CI/CD；
- Spring Security、JWT 和 OAuth；
- 微服务、Kafka 和 Redis；
- Kubernetes 和 Terraform；
- 云平台部署；
- 高级并发和 JVM 调优；
- 复杂设计模式。

## 8. 当前下一步

第 6 周验收结果：

- 完成 Thread、Runnable、`start()` 和 `join()` 实验；
- 使用 `synchronized` 保护两个线程共享的计数器；
- 能解释原子操作、race condition 和 lost update；
- 综合使用 Map、Lambda、Stream、排序、验证和 JUnit；
- 37 个 JUnit 测试通过；
- Java 基础关卡通过。

第 7 周当前进度：

- 已创建公开仓库 `https://github.com/Chx999/SecureStudy`；
- 已配置 `origin`，并让本地 `main` 跟踪 `origin/main`；
- 已完成首次 push；
- 通过 PR #1 将 `feature/remove-course` 合并到 `main`；
- 为删除课程功能添加成功和失败测试，共 39 个 JUnit 测试通过。

下一步：

> 人为制造一次安全的 merge conflict，理解冲突标记并手动解决。

## 9. 每周复盘模板

```markdown
### Week N

- 本周知识点：
- 完成的小练习：
- 加入 SecureStudy 的功能：
- 测试命令和结果：
- 遇到的错误与根因：
- 我能独立解释的内容：
- 英语技术表达：
- 西班牙语表达：
- 算法练习：
- 下周只做的第一步：
```

## 10. 调整规则

- 只有通过当前阶段验收后才进入下一阶段；
- 如果某周未完成，直接顺延，不把任务叠加到下一周；
- 如果连续两周负担过重，减少项目功能，不删除基础知识；
- 如果代码能运行但无法解释，优先复习而不是继续增加功能；
- 不因短期焦虑修改大三、大四或长期职业方向；
- 每次调整只改变当前学期执行节奏，不扩大技术范围。
