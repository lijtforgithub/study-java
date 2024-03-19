package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 打断线程 优雅的结束线程
 * 或者使用 volatile boolean running;
 *
 * @author LiJingTang
 * @date 2020-01-02 19:58
 */
class InterruptTest {

    /**
     * sleep/wait/join 调用这些方法的线程 调用interrupt会抛异常
     */
    @Test
    void sleep() {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("捕获了异常状态复位：" + Thread.currentThread().isInterrupted());
                    // 如果不重新调用中断方法 循环不能预期结束
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("结束循环");
                    break;
                }
            }
        }, "a");

        t.start();
        ThreadUtils.sleep(20, TimeUnit.MILLISECONDS);
        t.interrupt();

        ThreadUtils.sleepSeconds(5);
    }

    /**
     * 中断协商 只是设置中断标记 线程不会停止 线程可以根据此标记判断是否stop
     *
     * interrupt() 打断某个线程（设置标志位）
     * isInterrupted() 查询某个线程是否被打断过（查询标志位）
     * static interrupted() 查询当前线程是否被打断过 并重置打断标志
     */
    @Test
    void interrupt() {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().isInterrupted());

            for(;;) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程中断标记：" + Thread.currentThread().isInterrupted());
                    break;
                }
            }
        }, "a");

        t.start();
        ThreadUtils.sleep(1, TimeUnit.MILLISECONDS);
        t.interrupt();

        ThreadUtils.sleepSeconds(1);
        // 线程已经stop 也会返回false
        System.out.println(t.isInterrupted());
    }

    @Test
    void interrupted() {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().isInterrupted());
            for(;;) {
                // 查询并重置标记位
                if (Thread.interrupted()) {
                    System.out.println("线程中断标记：" + Thread.currentThread().isInterrupted());
                    break;
                }
            }
        }, "a");

        t.start();
        ThreadUtils.sleep(1, TimeUnit.MILLISECONDS);
        t.interrupt();
    }

}
