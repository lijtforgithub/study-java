package com.ljt.study.juc.pool;

import java.util.concurrent.*;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:22
 */
public class CallableTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            Future<?> runnableFuture = executor.submit(() -> System.out.println(Thread.currentThread().getName()));

            System.out.println(runnableFuture.get());

            Future<String> callableFuture = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                return "Result of Callable";
            });

            System.out.println(callableFuture.get());

            Future<String> cancelFuture = executor.submit(() -> {
                while (true) {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(10);
                }
            });

            Thread.sleep(100);
            System.out.println("future cancel: " + cancelFuture.cancel(true));

            Future<String> exceptionFuture = executor.submit(() -> {
                throw new Exception("Callable throw exception!");
            });

            System.out.println("task3: " + exceptionFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

}
