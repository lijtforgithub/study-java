package com.ljt.study.jvm.loading;

import org.junit.jupiter.api.Test;

/**
 * 用到这个类的时候才加载
 *
 * @author LiJingTang
 * @date 2019-11-17 22:23
 */
public class LazyLoadingTest {

    @Test
    public void test() {
        LazyTest lt;
    }

    @Test
    public void testNew() {
        LazyTest lt = new LazyTest();
    }

    @Test
    public void printI() {
        System.out.println(LazyTest.i);
    }

    @Test
    public void printJ() {
        System.out.println(LazyTest.j);
    }

    @Test
    public void classForName() throws ClassNotFoundException {
        Class.forName("com.ljt.study.jvm.loading.LazyTest");
    }

    static class P {
        static {
            System.out.println("P");
        }
    }

}

class LazyTest extends LazyLoadingTest.P {

    static final int i = 0;
    static int j = 9;

    static {
        System.out.println(LazyTest.class);
    }

}
