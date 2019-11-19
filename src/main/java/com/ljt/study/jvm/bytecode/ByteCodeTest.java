package com.ljt.study.jvm.bytecode;

import org.junit.jupiter.api.Test;

/**
 * 一些小测试例子
 *
 * @author LiJingTang
 * @date 2019-11-10 10:09
 */
public class ByteCodeTest {

    /**
     * 1.把数值入栈
     * 2.把数值赋值给局部变量表的变量
     * 3.把局部变量表的变量入栈
     * 4.把局部变量表的值自增
     * 5.把栈里的值赋值给局部变量表
     * 所以i的值最终被覆盖，没有变化
     */
    @Test
    public void test01() {
        int i = 0;
        i = i ++;
        System.out.println(i);
    }

}
