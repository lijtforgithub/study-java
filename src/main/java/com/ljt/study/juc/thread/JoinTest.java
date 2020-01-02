package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:41
 */
public class JoinTest {

    public static void main(String[] args) {
        JoinThread thread = new JoinThread("JOIN");
        thread.start();

        try {
            thread.join(); // 在当前线程等待thread线程终止，相等于方法调用
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
