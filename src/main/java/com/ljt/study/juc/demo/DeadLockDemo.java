package com.ljt.study.juc.demo;

import com.ljt.study.juc.ThreadUtils;

/**
 * 死锁
 *
 * @author LiJingTang
 * @date 2020-01-02 18:38
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLock(true));
        Thread t2 = new Thread(new DeadLock(false));
        t1.start();
        t2.start();
    }

    private static final Object obj1 = new Object(), obj2 = new Object();

    private static class DeadLock implements Runnable {

        private boolean flag;

        public DeadLock(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            System.out.println("flag = " + flag);
            if (flag) {
                synchronized (obj1) {
                    ThreadUtils.sleepSeconds(1);

                    synchronized (obj2) {
                        System.out.println("TRUE");
                    }
                }
            } else {
                synchronized (obj2) {
                    ThreadUtils.sleepSeconds(1);

                    synchronized (obj1) {
                        System.out.println("FALSE");
                    }
                }
            }
        }
    }

}
