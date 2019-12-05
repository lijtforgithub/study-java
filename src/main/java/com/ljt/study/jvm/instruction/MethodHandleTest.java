package com.ljt.study.jvm.instruction;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author LiJingTang
 * @date 2019-12-05 15:50
 */
public class MethodHandleTest {

    @Test
    public void testPrintln() throws Throwable {
        for (int i = 0; i < 5; i++) {
            Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new MHTest();
            getPrintlnMethodHandle(obj).invokeExact("Hell Java Dynamic");
        }
    }

    @Test
    public void testThinking() {
        new Son().thinking();
    }


    private static MethodHandle getPrintlnMethodHandle(Object receiver) throws NoSuchMethodException,
            IllegalAccessException {
        // MethodType 方法类型，包含了方法的返回值类型（第一个参数）和参数类型（第二个及以后的参数）
        MethodType mt = MethodType.methodType(void.class, String.class);
        // lookup() 在指定类中查找符合给定的方法名称、方法类型，并且符合调用权限的方法句柄
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt)
                // 这里调用的是一个虚方法，Java语言规则，方法第一个参数是隐式this；以前是在参数列表进行传递，现在使用bindTo。
                .bindTo(receiver);
    }

    private static class MHTest {

        public void println(String s) {
            System.out.println("MHTest: " + s);
        }
    }


    private static class GrandFather {

        void thinking() {
            System.out.println("I am grandfather");
        }
    }

    private static class Father extends GrandFather {

        @Override
        void thinking() {
            System.out.println("I am father");
        }
    }

    private static class Son extends Father {

        @Override
        void thinking() {
            // 实现调用祖父类的thinking()

            MethodType mt = MethodType.methodType(void.class);
            try {
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
