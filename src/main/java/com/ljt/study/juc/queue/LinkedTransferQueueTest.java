package com.ljt.study.juc.queue;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author LiJingTang
 * @date 2025-07-19 16:40
 */
public class LinkedTransferQueueTest {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        System.out.println(queue.add(1));
        System.out.println(queue.add(2));
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        queue.transfer(3);
    }

}
