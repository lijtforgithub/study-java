package com.ljt.study.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:27
 */
public class CallableAndFutureTest {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<String> future = threadPool.submit(() -> {
            System.out.println("1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("中断信号：" + Thread.currentThread().isInterrupted());
            }
            System.out.println("2");

            return "Hello,World!";
        });

        System.out.println("wait a result...");

        Thread.sleep(2000);
        System.out.println(future.cancel(true));

        if (!future.isCancelled()) {
            System.out.println("result = " + future.get());
        }

        System.out.println("end");
    }

}
