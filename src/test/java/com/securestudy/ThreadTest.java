package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadTest {
  @Test
  void runsTaskOnWorkerThread() throws InterruptedException {
    StringBuilder result = new StringBuilder();

    Runnable task = () -> result.append("done");
    Thread worker = new Thread(task);

    worker.start();
    worker.join();

    assertEquals("done", result.toString());

  }
}
