package com.ljt.study.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS的ABA问题（如果只是改数值 ABA问题可忽略）
 *
 * @author LiJingTang
 * @date 2019-11-22 11:14
 */
public class ABATest {

    private static AtomicInteger atomicInt = new AtomicInteger(100);
    // 加了版本戳
    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInt.compareAndSet(100, 101);
            atomicInt.compareAndSet(101, 100);
        });

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicInt.compareAndSet(100, 101)); // true
        });

        t1.start(); t2.start();

        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
        });

        Thread t4 = new Thread(() -> {
            int stamp = atomicStampedRef.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1)); // false
        });

        t3.start(); t4.start();
    }

}
