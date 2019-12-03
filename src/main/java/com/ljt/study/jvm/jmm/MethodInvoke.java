package com.ljt.study.jvm.jmm;

import org.junit.jupiter.api.Test;

/**
 * 方法调用
 *
 * @author LiJingTang
 * @date 2019-12-02 17:18
 */
public class MethodInvoke {

    /**
     * 静态分派（深入理解Java虚拟机P248）
     */
    @Test
    public void testStaticDispatch() {
        // Human-静态类型 Man/Woman-动态类型
        Human man = new Man();
        Human woman = new Woman();
        // 静态类型变化
        sayHello(man);
        sayHello(woman);
    }

    private static abstract class Human {

    }

    private static class Man extends Human {

    }

    private static class Woman extends Human {

    }

    private static void sayHello(Human guy) {
        System.out.println("Hello Guy");
    }

    private static void sayHello(Man guy) {
        System.out.println("Hello Gentleman");
    }

    private static void sayHello(Woman guy) {
        System.out.println("Hello Lady");
    }

}
