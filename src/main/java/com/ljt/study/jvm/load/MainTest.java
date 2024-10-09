package com.ljt.study.jvm.load;

import java.sql.DriverManager;

/**
 * 类的静态变量初始化
 *
 * @author LiJingTang
 * @date 2019-11-10 20:52
 */
public class MainTest {

    public static void main(String[] args) {
        System.out.println(DriverManager.class.getClassLoader());
        System.out.println(T1.i);
        System.out.println(T2.i);
    }

    private static class T1 {
        // 静态变量没有引用关系顺序赋值
        private static int i = 2;
        private static T1 t = new T1();

        private T1() {
            i++;
        }
    }

    private static class T2 {
        // 静态变量没有引用关系顺序赋值
        private static T2 t = new T2();
        private static int i = 2;

        private T2() {
            i++;
        }
    }

}
