package com.fish.javademo;

import android.os.Build;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;

public class ThreadPoolTest {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String args[]) {

        //直接构造ThreadPoolExecutor 方式
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            int ii = i;
            threadPoolExecutor.execute(() -> {
                System.out.println("runnable i=" + ii);
                System.out.println("active count:" + threadPoolExecutor.getActiveCount() + " pool size:" + threadPoolExecutor.getPoolSize());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //单个核心线程
        ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int ii = i;
            singleExecutorService.execute(() -> {
                System.out.println("runnable i=" + ii);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //固定线程数
        ExecutorService fixedExecutorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            int ii = i;
            fixedExecutorService.execute(() -> {
                int activeCount = ((ThreadPoolExecutor) fixedExecutorService).getActiveCount();
                System.out.println("runnable i=" + ii + " activeCount:" + activeCount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //cache 方式
        ExecutorService cacheExecutorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            int ii = i;
            cacheExecutorService.execute(() -> {
                int activeCount = ((ThreadPoolExecutor) cacheExecutorService).getActiveCount();
                System.out.println("runnable i=" + ii + " activeCount:" + activeCount);
            });
        }

        //schedule 方式 延时执行
        ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 10; i++) {
            int ii = i;
            scheduleExecutorService.schedule(() -> {
                int activeCount = ((ThreadPoolExecutor) scheduleExecutorService).getActiveCount();
                System.out.println("runnable i=" + ii + " activeCount:" + activeCount);
            }, 2, TimeUnit.SECONDS);
        }

        ExecutorService stealService = Executors.newWorkStealingPool();
        for (int i = 0; i < 10; i++) {
            int ii = i;
            stealService.execute(() -> {
                System.out.println("runnable i=" + ii);
            });
        }
    }
}
