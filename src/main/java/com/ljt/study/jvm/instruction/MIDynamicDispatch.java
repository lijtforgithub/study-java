package com.ljt.study.jvm.instruction;

/**
 * 方法调用-动态分配
 *
 * @author LiJingTang
 * @date 2019-12-04 17:02
 */
public class MIDynamicDispatch {

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();

        man = new Woman();
        man.sayHello();
    }

    private static abstract class Human {
        abstract void sayHello();
    }

    private static class Man extends MIDynamicDispatch.Human {

        @Override
        void sayHello() {
            System.out.println("Hello Gentleman");
        }
    }

    private static class Woman extends MIDynamicDispatch.Human {

        @Override
        void sayHello() {
            System.out.println("Hello Lady");
        }
    }

}
