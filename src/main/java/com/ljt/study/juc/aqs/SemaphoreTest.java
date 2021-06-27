package com.ljt.study.juc.aqs;

import com.ljt.study.juc.ThreadUtils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 控制线程的并发数量；可以用作限流
 *
 * @author LiJingTang
 * @date 2020-01-03 10:41
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int num = 3;
        final Semaphore semaphore = new Semaphore(num);

        // 10个线程过来 限流3个
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 进入，当前已有" + (3 - semaphore.availablePermits()) + "并发");
                ThreadUtils.sleepSeconds(new Random().nextInt(10));

                System.out.println(Thread.currentThread().getName() + " 即将离开");
                semaphore.release();
                // 下面代码有时候执行不正确，因为没有和上面的代码合成原子
                System.out.println(Thread.currentThread().getName() + " 已离开，当前已有" + (3 - semaphore.availablePermits()) + "并发");
            });
        }
    }

}
