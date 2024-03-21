package com.ljt.study.juc.thread.create;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:40
 */
class CreateWithRunnableTest {

    public static void main(String[] args) {
        // 一个线程任务也可以启动两个线程
        RunTask r = new RunTask();
        Thread t1 = new Thread(r, "run-task-1");
        Thread t2 = new Thread(r, "run-task-2");

        t1.start();
        t2.start();

        for (int i = 0; i < 50; i++) {
            System.out.println("Main thread loop of " + i);
        }
    }

    private static class RunTask implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                System.out.println("Sub thread[" + Thread.currentThread().getName() + "] loop of " + i + " " + Thread.currentThread().isAlive());
            }
        }
    }

}
