package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author LiJingTang
 * @date 2020-01-09 19:50
 */
class ThreadStateTest {

    private static final Object LOCK_O = new Object();
    private static final String MAIN = "main";


    @Test
    void testBlocked() {
        Thread t = new Thread(() -> {
            synchronized (LOCK_O) {
            }
        });

        synchronized (LOCK_O) {
            t.start();
            ThreadUtils.sleepSeconds(1);
            System.out.println(t.getState());
        }
    }

    /**
     * wait 线程进入等待队列 notify/notifyAll后 从等待队列 -> 锁池
     * wait 释放锁 进入锁池后重新竞争锁资源
     * WAITING
     */
    @Test
    void testWait() {
        WaitThread waitThread = new WaitThread();
        waitThread.start();

        // 睡1秒 防止 SleepThread 比 WaitThread 先执行
        ThreadUtils.sleepSeconds(1);

        // SleepThread 睡10秒 占用 wait 释放后的锁资源
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();

        // WaitThread 仍在等待队列中 等待被 notify
        ThreadUtils.printThreadStatus(waitThread.getName());

        // 睡15 证明 SleepThread 结束后 WaitThread 没有继续执行
        ThreadUtils.sleepSeconds(15);

        // 唤醒 WaitThread 不加此代码 不会打印 WaitThread end
        synchronized (LOCK_O) {
            LOCK_O.notify();
        }
    }

    /**
     * wait 线程进入等待队列 指定时间后 从等待队列 -> 锁池
     * wait 释放锁后 SleepThread 一直占用锁 sleep 结束后 WaitTimeThread 继续执行
     * TIMED_WAITING
     */
    @Test
    public void testWaitTime() {
        WaitTimeThread waitTimeThread = new WaitTimeThread();
        waitTimeThread.start();

        // 睡1秒 防止 SleepThread 比 WaitThread 先执行
        ThreadUtils.sleepSeconds(1);

        // SleepThread 睡10秒 占用 wait 释放后的锁资源 10秒之后 WaitTimeThread 在锁池获取到锁 继续执行
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();

        ThreadUtils.printThreadStatus(waitTimeThread.getName());
    }

    /**
     * sleep 不释放锁
     * TIMED_WAITING
     */
    @Test
    public void testSleep() {
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();

        // 睡1秒 确定 SleepThread 执行
        ThreadUtils.sleepSeconds(1);
        ThreadUtils.printThreadStatus(sleepThread.getName());

        synchronized (LOCK_O) {
            System.out.println("sleep 不释放锁 SleepThread 线程结束才释放锁");
        }
    }

    /**
     * join 等待调用 join 方法的线程执行结束 当前线程继续执行
     * WAITING
     */
    @Test
    public void testJoin() throws InterruptedException {
        JoinThread joinThread = new JoinThread();
        joinThread.start();
        joinThread.join();

        System.out.println("JoinThread 执行结束 当前线程继续执行");
    }

    /**
     * join 指定时间 如果指定时间后 调用 join 方法的线程未执行结束 当前线程继续执行
     * TIMED_WAITING
     */
    @Test
    public void testJoinTime() throws InterruptedException {
        JoinTimeThread joinTimeThread = new JoinTimeThread();
        joinTimeThread.start();

        // join
        joinTimeThread.join(5000);

        System.out.println("join 不释放锁 方法执行结束才释放锁");
    }

    /**
     * LockSupport.park 不释放锁
     * WAITING
     */
    @Test
    public void testLockSupportPark() {
        LockSupportParkThread parkThread = new LockSupportParkThread();
        parkThread.start();

        ThreadUtils.sleepSeconds(1);
        ThreadUtils.printThreadStatus(parkThread.getName());

        new GetLockThread().start();

        ThreadUtils.sleepSeconds(5);
        LockSupport.unpark(parkThread);
    }

    /**
     * LockSupport.parkNanos 不释放锁
     * TIMED_WAITING
     */
    @Test
    public void testLockSupportParkTime() {
        LockSupportParkNanosThread parkThread = new LockSupportParkNanosThread();
        parkThread.start();

        ThreadUtils.sleepSeconds(1);
        ThreadUtils.printThreadStatus(parkThread.getName());

        new GetLockThread().start();
    }


    private static class WaitThread extends Thread {

        public WaitThread() {
            super(WaitThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");

                try {
                    LOCK_O.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }

    private static class WaitTimeThread extends Thread {

        public WaitTimeThread() {
            super(WaitTimeThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");

                try {
                    LOCK_O.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }


    private static class SleepThread extends Thread {

        public SleepThread() {
            super(SleepThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");
                ThreadUtils.sleepSeconds(10);
                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }


    private static class JoinThread extends Thread {

        public JoinThread() {
            super(JoinThread.class.getSimpleName());
        }

        @Override
        public void run() {

            System.out.println(Thread.currentThread().getName() + " start");
            ThreadUtils.printThreadStatus(this.getName(), MAIN);
            System.out.println(Thread.currentThread().getName() + " end");
        }
    }

    private static class JoinTimeThread extends Thread {

        public JoinTimeThread() {
            super(JoinTimeThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");
                ThreadUtils.printThreadStatus(this.getName(), MAIN);

                // join 方法不释放锁 线程方法结束后才执行
                new GetLockThread().start();

                ThreadUtils.sleepSeconds(10);
                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }


    private static class GetLockThread extends Thread {

        public GetLockThread() {
            super(GetLockThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁 执行了 .... ");
            }
        }
    }


    private static class LockSupportParkThread extends Thread {

        public LockSupportParkThread() {
            super(LockSupportParkThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }

    private static class LockSupportParkNanosThread extends Thread {

        public LockSupportParkNanosThread() {
            super(LockSupportParkNanosThread.class.getSimpleName());
        }

        @Override
        public void run() {
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread().getName() + " start");
                long time = (long) Math.pow(10, 9);
                LockSupport.parkNanos(5 * time);
                System.out.println(Thread.currentThread().getName() + " end");
            }
        }
    }

    @AfterEach
    public void after() {
        ThreadUtils.sleepSeconds(30);
    }

}
