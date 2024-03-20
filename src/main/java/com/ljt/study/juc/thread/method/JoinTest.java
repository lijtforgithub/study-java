package com.ljt.study.juc.thread.method;

import com.ljt.study.juc.ThreadUtils;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:41
 */
class JoinTest {

    public static void main(String[] args) {
        JoinThread thread = new JoinThread("JOIN");
        thread.start();

        try {
            /**
             * 底层实现依赖wait
             *
             * while (isAlive()) {
             *    wait(0);
             * }
             *
             * As a thread terminates the this.notifyAll method is invoked.
             * It is recommended that applications not use wait, notify, or notifyAll on Thread instances.
             */
            thread.join(); // 方法会阻塞调用线程，直到被调用的线程执行完毕或者超时（如果传递了超时参数，调用线程会等待被调用线程执行，但如果超过了超时时间，它会继续执行。）。相等于方法调用
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Main Thread loop of " + i);
        }
    }

    private static class JoinThread extends Thread {

        public JoinThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                ThreadUtils.sleepSeconds(1);
                System.out.println(this.getName() + " Thread loop of " + i);
            }
        }
    }

}
