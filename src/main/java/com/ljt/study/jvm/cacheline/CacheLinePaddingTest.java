package com.ljt.study.jvm.cacheline;

import com.ljt.study.juc.ThreadUtils;

import static com.ljt.study.jvm.cacheline.CacheLineTest.COUNT;

/**
 * 填充缓存行
 *
 * @author LiJingTang
 * @date 2019-11-10 21:32
 */
public class CacheLinePaddingTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < COUNT; i++) {
                array[0].x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < COUNT; i++) {
                array[1].x = i;
            }
        });

        final long startTime = System.currentTimeMillis();
        t1.start();
        t2.start();
        ThreadUtils.join(t1, t2);
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static T[] array = new T[2];

    static {
        array[0] = new T();
        array[1] = new T();
    }

    private static class T extends Padding {
        volatile long x = 0L;
    }

    /**
     * 用7个long类型56byte填充/对齐能够提高效率
     * 一个数据占用一个缓存行
     */
    private static class Padding {
        private volatile long p1, p2, p3, p4, p5, p6, p7;
    }

}
