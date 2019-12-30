package com.ljt.study.juc.atomic;

import com.ljt.study.juc.ThreadUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS的ABA问题（如果只是改数值 ABA问题可忽略）
 *
 * @author LiJingTang
 * @date 2019-11-22 11:14
 */
public class ABATest {

    private static final AtomicInteger atomicInt = new AtomicInteger(100);
    // 加了版本戳
    private static final AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            ThreadUtils.sleepSeconds(1);
            atomicInt.compareAndSet(100, 101);
            atomicInt.compareAndSet(101, 100);
        });

        Thread t2 = new Thread(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(atomicInt.compareAndSet(100, 101)); // true
        });

        t1.start();
        t2.start();

        Thread t3 = new Thread(() -> {
            ThreadUtils.sleepSeconds(1);
            atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
        });

        Thread t4 = new Thread(() -> {
            int stamp = atomicStampedRef.getStamp();
            ThreadUtils.sleepSeconds(2);
            System.out.println(atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1)); // false
        });

        t3.start();
        t4.start();
    }

}
