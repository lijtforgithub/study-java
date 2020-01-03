package com.ljt.study.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author LiJingTang
 * @date 2020-01-03 11:29
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println(Thread.currentThread().getName() + "准备放数据");
                        queue.put(1);
                        System.out.println(Thread.currentThread().getName() + "队列目前有" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000)); // 此处的睡眠时间分别改为100和1000，观察结果
                        System.out.println(Thread.currentThread().getName() + "准备取数据");
                        queue.take();
                        System.out.println(Thread.currentThread().getName() + "队列目前有" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
