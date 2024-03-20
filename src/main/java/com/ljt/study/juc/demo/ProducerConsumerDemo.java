package com.ljt.study.juc.demo;

import com.ljt.study.juc.ThreadUtils;

/**
 * 生产者消费者
 *
 * @author LiJingTang
 * @date 2020-01-02 18:47
 */
public class ProducerConsumerDemo {

    public static void main(String[] args) {
        SyncStack stack = new SyncStack();
        Producer producer = new Producer(stack);
        Consumer consumer = new Consumer(stack);

        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    /**
     * 馒头
     */
    private static class ManTou {

        private int id;

        public ManTou(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "ManTou [id=" + id + "]";
        }
    }

    /**
     * 容器类
     */
    private static class SyncStack {

        private int index;
        private ManTou[] array = new ManTou[6];

        public synchronized void push(ManTou mt) {
            while (array.length == index) {
                try {
                    // 发生了阻塞性事件 容器满了
                    this.wait(); // 指的是使当前正在调用这个对象的线程等待(只有锁定对象才能等待即先synchronized)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.notify(); // 唤醒在此对象监视器上等待的单个线程(不能唤醒自己)。
            array[index++] = mt;
        }

        public synchronized ManTou pop() {
            while (0 == index) {
                try {
                    this.wait(); // wait之后当前这个对象的锁就不再拥有了，和sleep不一样（一直抱着锁）
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.notify();
            return array[--index];
        }
    }

    /**
     * 生产者
     */
    private static class Producer implements Runnable {

        private SyncStack stack;

        public Producer(SyncStack stack) {
            this.stack = stack;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                ManTou mt = new ManTou(i);
                this.stack.push(mt);
                System.out.println(this.getClass().getSimpleName() + " - " + mt);

                ThreadUtils.sleepSeconds(1);
            }
        }
    }

    /**
     * 消费者
     */
    private static class Consumer implements Runnable {

        private SyncStack stack;

        public Consumer(SyncStack stack) {
            this.stack = stack;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                ManTou mt = this.stack.pop();
                System.out.println(this.getClass().getSimpleName() + " - " + mt);

                ThreadUtils.sleepSeconds(1);
            }
        }
    }

}
