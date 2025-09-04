package com.ljt.study.juc.pool;

import java.util.concurrent.Executors;

/**
 * @author LiJingTang
 * @date 2025-07-27 16:47
 */
public class SingleThreadExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("子线程执行完毕");
        })).start();

        Thread.sleep(2000);
        System.gc();
    }

}
