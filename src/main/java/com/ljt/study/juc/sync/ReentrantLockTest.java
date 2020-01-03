package com.ljt.study.juc.sync;

import com.ljt.study.juc.ThreadUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LiJingTang
 * @date 2020-01-03 11:05
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        final OutPuter outputer = new ReentrantLockTest().getOutPuter();

        new Thread(() -> {
            while (true) {
                ThreadUtils.sleepSeconds(1);
                outputer.outPut("LiJingTang");
            }
        }).start();

        new Thread(() -> {
            while (true) {
                ThreadUtils.sleepSeconds(1);
                outputer.outPut("ChenPeiPei");
            }
        }).start();
    }

    public OutPuter getOutPuter() {
        return new OutPuter();
    }

    private static class OutPuter {

        private Lock lock = new ReentrantLock();

        public void outPut(String str) {
            lock.lock();
            try {
                if (null != str) {
                    for (int i = 0, len = str.length(); i < len; i++) {
                        System.out.print(str.charAt(i));
                    }
                    System.out.println();
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
