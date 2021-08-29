package com.fish.javademo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ThreadPool {
    public static void main(String args[]) {
        ThreadPool threadPool = new ThreadPool();
        threadPool.createThread();
        threadPool.startRunnable();
    }

    BlockingQueue<Runnable> shareQueue = new LinkedBlockingQueue<>();
    private void createThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Runnable targetRunnable = shareQueue.take();
                    targetRunnable.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void startRunnable() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行 runnable " + finalI);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            System.out.println("放入队列 runnable " + i);
            shareQueue.offer(runnable);
        }
    }
}
