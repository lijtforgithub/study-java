package com.ljt.study.juc.aqs;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

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
public class CountDownLatchTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdlOrder = new CountDownLatch(1);
        final CountDownLatch cdlAnswer = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Runnable command = () -> {
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "正准备接受命令");
                    cdlOrder.await();
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "回应命令处理结果");
                    cdlAnswer.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            service.execute(command);
        }

        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");
            cdlOrder.countDown();
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            cdlAnswer.await();
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

    @Test
    public void testPhaser() {
        ExecutorService service = Executors.newCachedThreadPool();
        final Phaser phaserOrder = new Phaser(1);
        final Phaser phaserAnswer = new Phaser(3);

        for (int i = 0; i < 3; i++) {
            Runnable command = () -> {
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "正准备接受命令");
                    phaserOrder.awaitAdvance(phaserOrder.getPhase());
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "回应命令处理结果");
                    phaserAnswer.arrive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            service.execute(command);
        }

        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");
            phaserOrder.arrive();
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            phaserAnswer.awaitAdvance(phaserAnswer.getPhase());
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
        ThreadUtils.sleepSeconds(30);
    }

}
