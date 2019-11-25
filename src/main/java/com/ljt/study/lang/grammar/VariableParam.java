package com.ljt.study.lang.grammar;

/**
 * 调用可变参数的方法时，编译时为可变参数隐含创建一个数组，在方法体中以数组的形式访问可变参数。
 * JDK1.5之前用数组实现相同功能
 *
 * @author LiJingTang
 * @date 2019-11-21 15:11
 */
public class VariableParam {

    public static void main(String[] args) {
        m(); // 不传参是长度为0的数组
        m(1, "String", new Object());
    }

    private static void m(Object... params) {
        for (Object obj : params) {
            System.out.println(obj);
        }
    }

}
