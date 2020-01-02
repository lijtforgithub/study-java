package com.ljt.study.juc.demo;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:14
 */
public class ShareDataDemo {

    public static void main(String[] args) {
        final ShareData data = new ShareData();
        new Thread(new IncrementData(data)).start();
        new Thread(new DecrementData(data)).start();
    }

    private static class IncrementData implements Runnable {

        private ShareData data;

        public IncrementData(ShareData data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                data.increment();
            }
        }
    }

    private static class DecrementData implements Runnable {

        private ShareData data;

        public DecrementData(ShareData data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                data.decrement();
            }
        }
    }

    private static class ShareData {

        private int j = 0;

        public synchronized void increment() {
            j++;
            System.out.println("j + 1 = " + j);
        }

        public synchronized void decrement() {
            j--;
            System.out.println("j - 1 = " + j);
        }
    }

    /**
     * 如果每个线程执行的代码相同，可以使用同一个Runnable对象，这个Runnable对象中有那个共享的数据。卖票系统就可以这么做
     */
    private static class Ticket implements Runnable {

        private int count = 100;

        @Override
        public void run() {
            this.saleTicket();
        }

        private synchronized void saleTicket() {
            while (count > 0) {
                count--;
                System.out.println("余票 - " + count);
            }
        }
    }

}
