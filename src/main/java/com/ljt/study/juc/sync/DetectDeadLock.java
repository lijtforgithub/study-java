package com.ljt.study.juc.sync;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 探测死锁
 *
 * 最好的办法是保存 JVM 信息、日志等“案发现场”的数据
 * 立刻重启服务，来尝试修复死锁
 * 排查死锁、修改代码，最终重新发布。
 *
 * @author LiJingTang
 * @date 2025-07-16 09:24
 */
public class DetectDeadLock implements Runnable {

    public int flag;
    static Object o1 = new Object();
    static Object o2 = new Object();

    public void run() {
        System.out.println(Thread.currentThread().getName() + " flag = " + flag);

        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (o2) {
                    System.out.println("线程1获得了两把锁");
                }
            }
        }

        if (flag == 2) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (o1) {
                    System.out.println("线程2获得了两把锁");
                }
            }
        }

    }

    public static void main(String[] argv) throws InterruptedException {

        DetectDeadLock r1 = new DetectDeadLock();
        DetectDeadLock r2 = new DetectDeadLock();
        r1.flag = 1;
        r2.flag = 2;
        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
        t1.start();
        t2.start();
        Thread.sleep(1000);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                System.out.println("线程id为" + threadInfo.getThreadId() + ",线程名为" + threadInfo.getThreadName()
                        + "的线程已经发生死锁，需要的锁" + threadInfo.getLockInfo().getClassName() + "正被线程" + threadInfo.getLockOwnerName() + "持有。");
            }
        }
    }

}
