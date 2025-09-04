package com.ljt.study.juc.thread.method;

import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2025-07-26 21:26
 */
public class WaitTest {

    /**
     * 解释join方法
     */
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " 结束");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "sub");

        t.start();

        synchronized (t) {
            System.out.println(Thread.currentThread().getName() + " 获取锁 开始阻塞");
            t.wait();
            System.out.println(t.getName() + " = " + t.getState() + " 唤醒 " + Thread.currentThread().getName());
        }

    }

}
