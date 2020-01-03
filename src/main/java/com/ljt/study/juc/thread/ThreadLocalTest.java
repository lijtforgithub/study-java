package com.ljt.study.juc.thread;

import java.util.Random;

/**
 * 线程间数据共享
 *
 * @author LiJingTang
 * @date 2020-01-03 08:36
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                int data = new Random().nextInt();
                System.out.println(Thread.currentThread().getName() + " has put data : " + data);
                threadLocal.set(data);
                ThreadDataObject.getThreadInstance().setId(data);
                ThreadDataObject.getThreadInstance().setName("Name-" + data);
                new A().getData();
                new B().getData();
            }).start();
        }
    }

    private static class A {
        public void getData() {
            int data = threadLocal.get();
            ThreadDataObject obj = ThreadDataObject.getThreadInstance();
            System.out.println("A from " + Thread.currentThread().getName() + " has put data : " + data + " ID: " + obj.getId() + " NAME: " + obj.getName());
        }
    }

    private static class B {
        public void getData() {
            int data = threadLocal.get();
            ThreadDataObject obj = ThreadDataObject.getThreadInstance();
            System.out.println("B from " + Thread.currentThread().getName() + " has put data : " + data + " ID: " + obj.getId() + " NAME: " + obj.getName());
        }
    }

    private static class ThreadDataObject {

        private static ThreadDataObject instance;
        private static ThreadLocal<ThreadDataObject> dataMap = new ThreadLocal<>();
        private int id;
        private String name;

        private ThreadDataObject() {
        } // 隐藏构造方法

        /**
         * 单例模式-饥汉模式
         */
        public static synchronized ThreadDataObject getInstance() {
            if (null == instance) {
                instance = new ThreadDataObject();
            }

            return instance;
        }

        public static ThreadDataObject getThreadInstance() {
            ThreadDataObject obj = dataMap.get();

            if (null == obj) {
                obj = new ThreadDataObject();
                dataMap.set(obj);
            }

            return obj;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
