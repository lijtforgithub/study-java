package com.ljt.study.jvm.instruction;

/**
 * @author LiJingTang
 * @date 2019-11-25 20:38
 */
public class InvokeDynamic {

    public static void main(String[] args) {
        I i1 = C::n;
        I i2 = C::n;
        I i3 = C::n;

        i1.m();
        // Lambda表达式动态产生类
        System.out.println(i1.getClass());
        System.out.println(i2.getClass());
        System.out.println(i3.getClass());
        System.out.println(i1.getClass() == i3.getClass());
    }

    @FunctionalInterface
    private interface I {
        void m();
    }

    private static class C {
        private static void n() {
            System.out.println("invokedynamic指令");
        }
    }

}
