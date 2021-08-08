package com.fish.javademo;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class ThreadRet {
    private int sum = 0;

    public static void main(String args[]) {
        ThreadRet threadRet = new ThreadRet();
//        threadRet.startTest();
//        threadRet.startCall();
        threadRet.startPool();
    }

    private void startTest() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 5;
                int b = 5;
                int c = a + b;
                //将结果赋予成员变量
                sum = c;
                System.out.println("c:" + c);
            }
        });
        t1.start();

        try {
            //等待线程执行完毕
            t1.join();
            //执行过这条语句后，说明线程已经将sum值执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sum:" + sum);
    }

    private void startCall() {
        //定义Callable，具体的线程处理在call()里进行
        Callable<String> callable = new Callable() {
            @Override
            public Object call() throws Exception {
                String result = "hello world";
                //返回result
                return result;
            }
        };

        //定义FutureTask,持有Callable 引用
        FutureTask<String> futureTask = new FutureTask(callable);

        //开启线程
        new Thread(futureTask).start();

        try {
            //获取结果
            String result = futureTask.get();
            System.out.println("result:" + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startPool() {
        //线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        //定义Callable
        Callable<String> callable = new Callable() {
            @Override
            public Object call() throws Exception {
                String result = "hello world";
                //返回result
                return result;
            }
        };
        //返回Future，实际上是FutureTask实例
        //第一种放肆
        Future<String> future = service.submit(callable);
        //第二种方式
//        Future<String> future = (Future<String>) service.submit(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//          第三种方式
////        Future<String> future = service.submit(new Runnable() {
////            @Override
////            public void run() {
////
////            }
////        }, "nihao");

        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
