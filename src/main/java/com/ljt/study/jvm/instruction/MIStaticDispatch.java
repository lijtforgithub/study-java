package com.ljt.study.jvm.instruction;

import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * 方法调用-静态分派（深入理解Java虚拟机P248）
 *
 * @author LiJingTang
 * @date 2019-12-02 17:18
 */
public class MIStaticDispatch {

    @Test
    public void testStaticDispatch() {
        // Human-静态类型 Man/Woman-动态类型
        Human man = new Man();
        Human woman = new Woman();
        // 静态类型变化
        sayHello(man);
        sayHello(woman);
    }

    @Test
    public void testOverload() {
        // 类型转换 char -> int -> long -> float -> double 如果由float和double重载的方法会执行
        // 优先级 char -> int -> long -> Character -> Serializable -> Object -> char...
        sayHello('a');
    }

    private void sayHello(Object obj) {
        System.out.println("Hello Object");
    }

    private void sayHello(int i) {
        System.out.println("Hello int");
    }

    private void sayHello(long l) {
        System.out.println("Hello long");
    }

    private void sayHello(Character charset) {
        System.out.println("Hello Character");
    }

    private void sayHello(char c) {
        System.out.println("Hello char");
    }

    private void sayHello(char... c) {
        System.out.println("Hello char...");
    }

    private void sayHello(int... c) {
        System.out.println("Hello int...");
    }

    private void sayHello(Serializable s) {
        System.out.println("Hello Serializable");
    }


    /**
     * 和 Serializable 同时出现
     * 产生类型模糊编译错误
     */
   /* private void sayHello(Comparable c) {
        System.out.println("Hello Comparable");
    }*/

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
