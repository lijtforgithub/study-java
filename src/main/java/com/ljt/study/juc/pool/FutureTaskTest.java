package com.ljt.study.juc.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:26
 */
public class FutureTaskTest {

    public static void main(String[] args) throws Exception {
        Callable<String> callable = () -> {
            System.out.println("Sleep start...");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Sleep end...");

            return "time=" + System.currentTimeMillis();
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("Thread : result = " + futureTask.get());

        Executors.newSingleThreadExecutor().submit(futureTask);
        System.out.println("Executors : result = " + futureTask.get());
    }

    @Test
    public void testRunnable() {
        new Thread(new FutureTask<String>(() -> {
            System.out.println("Callable 结合 Thread");
            return null;
        })).start();
    }

}
