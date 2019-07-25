package com.example.util;

public class AsyncRunner {
    public void run(Runnable runnable) {
        Thread thread = new Thread(() -> {
            while (true) {
                runnable.run();
            }
        });
        thread.start();
    }
}
