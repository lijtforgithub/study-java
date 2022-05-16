package com.ljt.study.juc.thread;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:40
 */
class RunnableTest {

    public static void main(String[] args) {
        // 一个线程任务也可以启动两个线程
        Runner r = new Runner();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);

        thread1.start();
        thread2.start();

        for (int i = 0; i < 50; i++) {
            System.out.println("Main thread loop of " + i);
        }
    }

    private static class Runner implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                System.out.println("Sub thread loop of " + i + " " + Thread.currentThread().isAlive());
            }
        }
    }

}
