package com.ljt.study.juc.thread;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import static com.ljt.study.juc.ThreadUtils.sleepSeconds;

/**
 * @author LiJingTang
 * @date 2019-11-24 14:54
 */
class DaemonThreadTest {

    public static void main(String[] args) {
        Thread t = t1();
        // 守护线程，main 方法不等线程执行完成会提前结束。
        t.setDaemon(true);
        t.start();

        printThreadEnd();
    }

    private static void printThreadEnd() {
        System.out.println(Thread.currentThread().getName() + " end");
    }

    private static Thread t1() {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            sleepSeconds(5);
            printThreadEnd();
        }, "daemon-thread");
    }

    @Test
    void test() {
        Thread t = t1();
        t.start();
        ThreadUtils.join(t);

        printThreadEnd();
    }

}
