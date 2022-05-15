package com.ljt.study.juc.tool;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 门闩
 *
 * @author LiJingTang
 * @date 2020-01-03 10:35
 */
class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int num = 3;
        final CountDownLatch orderLatch = new CountDownLatch(1);
        final CountDownLatch answerLatch = new CountDownLatch(num);

        for (int i = 0; i < num; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 正准备接受命令");
                    orderLatch.await();
                    ThreadUtils.sleepSeconds(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " 回应命令处理结果");
                    answerLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadUtils.sleepSeconds(new Random().nextInt(10));
        System.out.println(Thread.currentThread().getName() + " 即将发布命令");
        orderLatch.countDown();
        System.out.println(Thread.currentThread().getName() + " 已发送命令，正在等待结果");
        answerLatch.await();
        System.out.println(Thread.currentThread().getName() + " 已收到所有响应结果");

        executor.shutdown();
    }

    @Test
    void usePhaser() {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int num = 3;
        final Phaser orderPhaser = new Phaser(1);
        final Phaser answerPhaser = new Phaser(num);

        for (int i = 0; i < num; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 正准备接受命令");
                orderPhaser.awaitAdvance(orderPhaser.getPhase());
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " 回应命令处理结果");
                answerPhaser.arrive();
            });
        }

        ThreadUtils.sleepSeconds(new Random().nextInt(10));
        System.out.println(Thread.currentThread().getName() + " 即将发布命令");
        orderPhaser.arrive();
        System.out.println(Thread.currentThread().getName() + " 已发送命令，正在等待结果");
        answerPhaser.awaitAdvance(answerPhaser.getPhase());
        System.out.println(Thread.currentThread().getName() + " 已收到所有响应结果");

        executor.shutdown();

        // 测试线程会提前结束
        ThreadUtils.sleepSeconds(30);
    }

}
