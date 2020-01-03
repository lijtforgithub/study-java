package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;

import java.util.Date;

/**
 * 打断线程
 *
 * @author LiJingTang
 * @date 2020-01-02 19:58
 */
public class InterruptTest {

    public static void main(String[] args) {
        InterruptThread1 t1 = new InterruptThread1();
        InterruptThread2 t2 = new InterruptThread2();
        t1.start();
        t2.start();

        ThreadUtils.sleepSeconds(5); // // 主线程睡10秒 (在哪个线程里调用睡眠哪个线程)

        t1.interrupt();
        t2.flag = false;
    }

    private static class InterruptThread1 extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(this.getClass().getSimpleName() + " - " + new Date());
                } catch (InterruptedException e) {
                    System.out.println(this.getName() + " interrupt");
                    return; // 结束线程
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
                    return; // 结束线程
                }
            }
        }
    }

}
