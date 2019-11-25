package com.ljt.study.jvm.bytecode;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-11-25 11:01
 */
public class MainTest {

    /**
     * 0 iconst_0          常量0压栈
     * 1 istore_1          把0赋值给局部变量表1位置变量并出栈
     * 2 iload_1           把局部变量表1位置变量压栈（0）
     * 3 iinc 1 by 1       把局部变量表1位置变量做+1
     * 6 istore_1          把栈顶（0）赋值给局部变量表1位置变量并出栈
     */
    @Test
    public void testIPP() {
        int i = 0;
        i = i++;
        System.out.println(i);
    }

    /**
     * 0 iconst_0          常量0压栈
     * 1 istore_1          把0赋值给局部变量表1位置变量并出栈
     * 2 iinc 1 by 1       把局部变量表1位置变量做+1
     * 5 iload_1           把局部变量表1位置变量压栈（1）
     * 6 istore_1          把栈顶（1）赋值给局部变量表1位置变量并出栈
     */
    @Test
    public void testPPI() {
        int i = 0;
        i = ++i;
        System.out.println(i);
    }

}
