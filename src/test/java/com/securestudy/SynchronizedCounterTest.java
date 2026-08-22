package com.securestudy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SynchronizedCounterTest {
  @Test
  void incrementsSafelyFromTwoThreads() throws InterruptedException {
    SynchronizedCounter counter = new SynchronizedCounter();
    Runnable task = () -> {
      for (int i = 0; i < 10_000; i++) {
        counter.increment();
      }
    };

    Thread first = new Thread(task);
    Thread second = new Thread(task);

    first.start();
    second.start();

    first.join();
    second.join();

    assertEquals(20_000, counter.getValue());
  }
}
