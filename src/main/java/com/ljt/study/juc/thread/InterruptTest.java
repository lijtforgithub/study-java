package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * 打断线程 优雅的结束线程
 *
 * @author LiJingTang
 * @date 2020-01-02 19:58
 */
class InterruptTest {

    public static void main(String[] args) {
        InterruptThread1 t1 = new InterruptThread1();
        InterruptThread2 t2 = new InterruptThread2();
        t1.start();
        t2.start();
        // 主线程睡10秒 (在哪个线程里调用睡眠哪个线程)
        ThreadUtils.sleepSeconds(5);

        t1.interrupt();
        t2.flag = false;
    }

    /**
     * sleep/wait/join 调用这些方法的线程 调用interrupt会抛异常
     */
    private static class InterruptThread1 extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(this.getClass().getSimpleName() + " - " + new Date());
                } catch (InterruptedException e) {
                    System.out.println(this.getName() + " interrupt");
                    System.out.println("捕获了异常状态复位：" + Thread.currentThread().isInterrupted());
                    // 结束线程
                    return;
                }
            }
        }
    }

    private static class InterruptThread2 extends Thread {

        boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                try {
                    Thread.sleep(1000);
                    System.out.println(this.getClass().getSimpleName() + " - " + new Date());
                } catch (InterruptedException e) {
                    System.out.println(this.getName() + " interrupt");
                    return;
                }
            }
        }
    }

    /**
     * 中断协商
     *
     * interrupt() 打断某个线程（设置标志位）
     * isInterrupted() 查询某个线程是否被打断过（查询标志位）
     * static interrupted() 查询当前线程是否被打断过 并重置打断标志
     */
    @Test
    void testIsInterrupted() {
        Thread t = new Thread(() -> {
            for(;;) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread is interrupted");
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            }
        });

        t.start();
        ThreadUtils.sleepSeconds(2);
        t.interrupt();
    }

    @Test
    void testInterrupted() {
        Thread t = new Thread(() -> {
            for(;;) {
                if (Thread.interrupted()) {
                    System.out.println("Thread is interrupted");
                    System.out.println(Thread.interrupted());
                }
            }
        });

        t.start();
        ThreadUtils.sleepSeconds(2);
        t.interrupt();
    }

}
