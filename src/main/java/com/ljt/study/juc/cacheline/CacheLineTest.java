package com.ljt.study.juc.cacheline;

/**
 * CPU读取缓存以cache line为基本单位 64byte
 *
 * @author LiJingTang
 * @date 2019-11-10 21:02
 */
public class CacheLineTest {

    static long COUNT = 1000_0000L;

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> {
            for (long i = 0; i < COUNT; i++) {
                array[0].x = i;
            }
        });
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                array[1].x = i;
            }
        });

        final long startTime = System.currentTimeMillis();
        t0.start(); t1.start();
        t0.join(); t1.join();
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static T[] array = new T[2];

    static {
        array[0] = new T();
        array[1] = new T();
    }

    /**
     * 位于同一缓存行的两个不同数据，被两个不同CPU锁定，产生互相影响的伪共享问题
     */
    private static class T {
        volatile long x = 0L;
    }

}
