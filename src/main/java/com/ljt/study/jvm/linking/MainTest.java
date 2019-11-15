package com.ljt.study.jvm.linking;

/**
 * 类的静态变量初始化
 *
 * @author LiJingTang
 * @date 2019-11-10 20:52
 */
public class MainTest {

    public static void main(String[] args) {
        System.out.println(T1.i);
        System.out.println(T2.i);
    }

}

class T1 {
    static int i = 2;
    private static T1 t = new T1();

    private T1() {
        i++;
    }

}

class T2 {
    private static T2 t = new T2();
    static int i = 2;

    private T2() {
        i++;
    }

}
