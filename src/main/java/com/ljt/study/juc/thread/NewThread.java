package com.ljt.study.juc.thread;

/**
 * @author LiJingTang
 * @date 2019-11-24 14:54
 */
public class NewThread {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 结束");
        }, "子线程").start();

        System.out.println(Thread.currentThread().getName() + " 结束");
    }

}
