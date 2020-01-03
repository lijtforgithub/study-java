package com.ljt.study.juc.pool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:27
 */
public class CallableAndFutureTest {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<String> future = threadPool.submit(() -> {
            Thread.sleep(2000);
            return "Hello,World!";
        });

        System.out.println("wait a result...");

        System.out.println("result = " + future.get());

        threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool);
        for (int i = 1; i <= 10; i++) {
            final int seq = i;
            completionService.submit(() -> {
                Thread.sleep(new Random().nextInt(5000));
                return seq;
            });
        }

        for (int i = 0; i <= 10; i++) {
            System.out.println(completionService.take().get());
        }
    }

}
