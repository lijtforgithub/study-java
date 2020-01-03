package com.ljt.study.juc.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LiJingTang
 * @date 2020-01-03 11:12
 */
public class ConditionTest {

    public static void main(String[] args) {
        final ConditionBusiness business = new ConditionBusiness();

        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                business.sub(i);
            }
        }).start();

        for (int i = 1; i <= 20; i++) {
            business.main(i);
        }
    }

    /**
     * 帮互斥的任务设计在一个业务类里，让他们自己处理状态。不是写在线程上的，而是写在线程要访问的资源上。
     * 这样多个线程调用都不用处理他们的互斥的状态。
     */
    private static class ConditionBusiness {

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        private boolean isShouldSubRun = true;

        public void main(int i) {
            lock.lock();

            try {
                while (!isShouldSubRun) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int j = 1; j <= 15; j++) {
                    System.out.println("Main thread sequence " + j + " , loop of " + i);
                }

                isShouldSubRun = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public synchronized void sub(int i) {
            lock.lock();

            try {
                while (isShouldSubRun) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 10; j++) {
                    System.out.println("Sub thread sequence " + j + " , loop of " + i);
                }

                isShouldSubRun = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }


    private static class BoundedBuffer {

        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[100];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length) {
                    notFull.await();
                }
                items[putptr] = x;
                if (++putptr == items.length) {
                    putptr = 0;
                }
                ++count;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                Object x = items[takeptr];
                if (++takeptr == items.length) {
                    takeptr = 0;
                }
                --count;
                notFull.signal();
                return x;
            } finally {
                lock.unlock();
            }
        }
    }

}
