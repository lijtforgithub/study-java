package com.ljt.study.juc.sync;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * volatile 可见性
 * <p>
 * 当一个变量被 volatile 修饰时，任何线程对它的写操作都会立即刷新到主内存中，
 * 并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据。
 *
 * @author LiJingTang
 * @date 2020-01-03 09:00
 */
class VolatileTest {

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        VolatileThread t = test.new VolatileThread();
        t.start();
        ThreadUtils.sleepSeconds(1);
        System.out.println("wakeup");
        test.running = false;
    }

    private
    volatile
    boolean running = true;

    private class VolatileThread extends Thread {

        @Override
        public void run() {
            System.out.println("start");
            while (running) {
                // 如果加了打印语句 实现有 synchronized 也会保持可见（synchronized在获取锁资源后也会使cpu缓存行失效）
                System.out.println("OK");
            }
            System.out.println("end");
        }
    }

    @Test
    public void testNotAtomic() throws InterruptedException {
        final int THREAD_SIZE = 10;
        CountDownLatch latch = new CountDownLatch(THREAD_SIZE);

        final NotAtomic notAtomic = new NotAtomic();
        for (int i = 0; i < THREAD_SIZE; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    notAtomic.increase();
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println(NotAtomic.count);
    }

    /**
     * volatile 不是原子性的
     */
    private static class NotAtomic {

        static volatile int count = 0;

        void increase() {
            count++;
        }
    }

}
