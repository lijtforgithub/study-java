package com.ljt.study.jvm.load;

import org.junit.jupiter.api.Test;

/**
 * 类初始化阶段
 *
 * @author LiJingTang
 * @date 2019-11-17 22:23
 */
class LazyInitTest {

    @Test
    void test() {
        Child c;
    }

    @Test
    void testNew() {
        new Child();
    }

    @Test
    void finalVar() {
        System.out.println(Child.i);
    }

    @Test
    void var() {
        System.out.println(Child.j);
    }

    @Test
    void classForName() throws ClassNotFoundException {
        Class.forName("com.ljt.study.jvm.load.LazyInitTest$Child");
    }

    @Test
    void classForNameFalse() throws ClassNotFoundException {
        Class.forName("com.ljt.study.jvm.load.LazyInitTest$Child", false, LazyInitTest.class.getClassLoader());
    }


    private static class Parent {

        static {
            System.out.println("父类静态块");
        }
    }

    private static class Child extends Parent {

        static final int i = 0;
        static int j = 1;

        static {
            System.out.println("子类静态块");
        }
    }

}
