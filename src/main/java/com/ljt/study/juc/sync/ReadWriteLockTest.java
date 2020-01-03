package com.ljt.study.juc.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LiJingTang
 * @date 2020-01-03 11:09
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        final LockQueue queue = new LockQueue();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> queue.get()).start();
            new Thread(() -> queue.put(new Random().nextInt(10000))).start();
        }
    }

    private static class LockQueue {

        private Object data = null; // 共享数据，只能有一个线程能写数据，但可以有多个线程同时读数据。
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public void get() {
            lock.readLock().lock(); // 上读锁，其他线程只能读不能写

            try {
                System.out.println(Thread.currentThread().getName() + " be ready to read data!");
                Thread.sleep((long) Math.random() * 1000);
                System.out.println(Thread.currentThread().getName() + " have read data : " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }

        public void put(Object data) {
            lock.writeLock().lock(); // 上写锁，不允许其他线程读也不允许写

            try {
                System.out.println(Thread.currentThread().getName() + " be ready to write data!");
                Thread.sleep((long) Math.random() * 1000);
                this.data = data;
                System.out.println(Thread.currentThread().getName() + " have write data : " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }


    private static class CacheDemo {

        private Map<String, Object> cacheMap = new HashMap<>();
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public Object getData(String key) {
            lock.readLock().lock();
            Object value = null;

            try {
                value = cacheMap.get(key);
                if (null == value) {
                    lock.readLock().unlock();
                    lock.writeLock().lock();

                    try {
                        if (!cacheMap.containsKey(key)) {
                            value = ""; // 获取真实的数据
                            cacheMap.put(key, value);
                        }
                    } finally {
                        lock.writeLock().unlock();
                    }

                    lock.readLock().lock();
                }
            } finally {
                lock.readLock().unlock();
            }

            return value;
        }
    }

}
