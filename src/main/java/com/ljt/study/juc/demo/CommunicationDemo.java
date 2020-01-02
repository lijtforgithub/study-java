package com.ljt.study.juc.demo;

/**
 * 线程通信
 *
 * @author LiJingTang
 * @date 2020-01-02 19:06
 */
public class CommunicationDemo {

    public static void main(String[] args) {
        final CommunicationBusiness business = new CommunicationBusiness();

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
    private static class CommunicationBusiness {

        private boolean isShouldSubRun = true;

        public synchronized void main(int i) {
            while (!isShouldSubRun) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 1; j <= 15; j++) {
                System.out.println("Main thread sequence " + j + " , loop of " + i);
            }

            isShouldSubRun = false;
            this.notify();
        }

        public synchronized void sub(int i) {
            while (isShouldSubRun) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("Sub thread sequence " + j + " , loop of " + i);
            }

            isShouldSubRun = true;
            this.notify();
        }
    }

}
