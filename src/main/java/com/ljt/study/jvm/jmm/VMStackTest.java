package com.ljt.study.jvm.jmm;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-12-02 13:29
 */
public class VMStackTest {

    /**
     * -verbose:gc
     */
    @Test
    public void testLocalVarTable() {
        {
            byte[] bytes = new byte[1024 * 1024 * 64];
        }

        System.gc();
    }

    /**
     * 如果一个方法，后面的代码有一些耗时很长的操作，前面又定义了占用大量内存、实际上已经不会再使用的变量，
     * 手动将其设置为 null（代提 int i = 0;）;可以加快内存回收。
     * 使用赋null值的操作来优化内存回收是建立在对字节码执行引擎概念模型的理解之上的。（深入理解Java虚拟机P241）
     *
     * -verbose:gc
     */
    @Test
    public void testLocalVarTableGC() {
        {
            byte[] bytes = new byte[1024 * 1024 * 64];
        }

        int i = 0; // bytes 超出作用域 占用的slot被复用 占用的内存会被回收

        System.gc();
    }

}
