package com.securestudy;

class SynchronizedCounter {
  private int value;

  synchronized void increment() {
    value++;
  }

  int getValue() {
    return value;
  }
}
