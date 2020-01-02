package com.ljt.study.juc.thread;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:36
 */
public class YieldTest {

    public static void main(String[] args) {
        YieldThread t1 = new YieldThread("YieldThread - 1");
        YieldThread t2 = new YieldThread("YieldThread - 2");
        t1.start();
        t2.start();
    }

    private static class YieldThread extends Thread {

        public YieldThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(this.getName() + " Thread loop of " + i);
                if (0 == i % 10) {
                    Thread.yield(); // 暂停(一次)当前正在执行的线程对象，并执行其他线程。
                }
            }
        }
    }

}
