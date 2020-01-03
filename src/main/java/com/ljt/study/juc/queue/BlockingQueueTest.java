package com.ljt.study.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用阻塞队列实现线程通信
 *
 * @author LiJingTang
 * @date 2020-01-03 12:13
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final QueueBusiness business = new QueueBusiness();

        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                business.sub(i);
            }
        }).start();

        for (int i = 1; i <= 20; i++) {
            business.main(i);
        }
    }

    /**
     * 把互斥的任务设计在一个业务类里，让他们自己处理状态。不是写在线程上的，而是写在线程要访问的资源上。
     * 这样多个线程调用都不用处理他们的互斥的状态。
     */
    private static class QueueBusiness {
        private BlockingQueue<Integer> mainQueue = new ArrayBlockingQueue<>(1);
        private BlockingQueue<Integer> subQueue = new ArrayBlockingQueue<>(1);

        { // 构造代码块 在任何构造方法执行之前
            try {
                subQueue.put(1); // 子任务先阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void main(int i) {
            try {
                mainQueue.put(1);

                for (int j = 1; j <= 15; j++) {
                    System.out.println("Main thread sequence " + j + " , loop of " + i);
                }

                subQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sub(int i) {
            try {
                subQueue.put(1);

                for (int j = 1; j <= 10; j++) {
                    System.out.println("Sub thread sequence " + j + " , loop of " + i);
                }

                mainQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
