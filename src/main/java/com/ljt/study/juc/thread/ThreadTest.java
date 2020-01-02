package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:43
 */
public class ThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("1: " + Thread.currentThread().getName());
                System.out.println("2: " + this.getName());
            }
        };
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3: " + Thread.currentThread().getName());
                System.out.println("4: " + (this instanceof Runnable)); // this 指代Runnable
            }
        });
        t2.start();

        /**
         *  public void run() {if (target != null) {target.run();}}
         *  调用父类Thread的run()方法才会执行Runnable即target里的run()
         */
        Thread t3 = new Thread(() -> {
            ThreadUtils.sleepSeconds(1);
            System.out.println("Runnable: " + Thread.currentThread().getName());
        }) {
            @Override
            public void run() {
                ThreadUtils.sleepSeconds(1);
                System.out.println("Thread: " + Thread.currentThread().getName());
            }
        };
        t3.start();
    }

}
