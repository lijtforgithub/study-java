package com.ljt.study.juc.sync;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LiJingTang
 * @date 2022-05-16 15:30
 */
class WaitNotifyTest {

    @Test
    void synchWaitNotify() {
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始阻塞");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 线程结束");
            }
        }, "a").start();

        ThreadUtils.sleepSeconds(2);
        new Thread(() -> {
            synchronized (lock) {
                lock.notify();
                System.out.println("唤醒");
            }
        }).start();
    }

    @Test
    void condition() {
        final ReentrantLock lock = new ReentrantLock();
        /**
         * Condition能够支持不响应中断，而通过使用Object方式不支持；
         * Condition能够支持多个等待队列（new 多个Condition对象），而Object方式只能支持一个；
         * Condition能够支持超时时间的设置，而Object不支持
         */
        final Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 开始阻塞");
                try {
//                    condition.awaitUninterruptibly();
                    // 会释放锁
                    condition.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 线程结束");
            } finally {
                lock.unlock();
            }
        }, "a").start();

        ThreadUtils.sleepSeconds(2);
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println("唤醒");
            } finally {
                lock.unlock();
            }
        }).start();
    }

    @Test
    void lockSupport() {
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 开始阻塞");
            LockSupport.park();

            System.out.println(Thread.currentThread().getName() + " 线程结束");
        }, "a");

        t.start();

        ThreadUtils.sleepSeconds(2);
        new Thread(() -> {
            LockSupport.unpark(t);
            System.out.println("唤醒");
        }).start();
    }

}

