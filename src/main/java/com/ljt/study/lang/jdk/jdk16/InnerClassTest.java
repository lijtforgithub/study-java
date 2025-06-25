package com.ljt.study.lang.jdk.jdk16;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:28
 */
public class InnerClassTest {

    public class Outer {
        class Inner {
            // 非静态内部类可以定义非常量的静态成员。
            static int age;
        }
    }

}
