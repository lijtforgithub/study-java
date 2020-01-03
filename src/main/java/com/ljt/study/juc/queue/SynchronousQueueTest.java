package com.ljt.study.juc.queue;

import com.ljt.study.juc.ThreadUtils;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LiJingTang
 * @date 2020-01-03 11:31
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {
        // true保证生产或消费者线程以FIFO的顺序访问。
        SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);

        for (int i = 0; i < 3; ++i) {
            new Customer(queue).start();
        }
        for (int i = 0; i < 3; ++i) {
            new Product(queue).start();
        }
    }

    private static class Product extends Thread {
        private SynchronousQueue<Integer> queue;

        public Product(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                int rand = ThreadLocalRandom.current().nextInt(1000);
                System.out.println("Thread Id:" + getId() + "  生产了一个产品：" + rand);
                System.out.println("Thread Id:" + getId() + " 等待两秒后运送出去...");

                ThreadUtils.sleepSeconds(2);
                /*
                 * offer()往queue里放一个element后立即返回，如果碰巧这个element被另一个thread取走了，
                 * offer方法返回true，认为offer成功；否则返回false。
                 * 也就是说offer不一定真正的插入的队列中，肯定没成功丢失了
                 */

                // queue.offer(rand); //注意offer与put方法的区别
                try {
                    /*
                     * put()往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。
                     */
                    this.queue.put(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Customer extends Thread {
        private SynchronousQueue<Integer> queue;

        public Customer(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 线程运行到queue.take()阻塞，直到Product生产一个产品queue.offer。
                    System.out.println("Thread Id:" + getId() + " 消费了一个产品:" + this.queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("------------------------------------------");
            }
        }
    }

}
