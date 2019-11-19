package com.ljt.study.jvm.load;

import org.junit.jupiter.api.Test;

/**
 * 类初始化阶段
 *
 * @author LiJingTang
 * @date 2019-11-17 22:23
 */
public class LazyInitTest {

    @Test
    public void test() {
        Child c;
    }

    @Test
    public void testNew() {
        new Child();
    }

    @Test
    public void testFinalVar() {
        System.out.println(Child.i);
    }

    @Test
    public void testVar() {
        System.out.println(Child.j);
    }

    @Test
    public void testClassForName() throws ClassNotFoundException {
        Class.forName("com.ljt.study.jvm.load.LazyInitTest$Child");
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
