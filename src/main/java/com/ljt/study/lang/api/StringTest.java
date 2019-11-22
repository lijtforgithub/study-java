package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  "" 双引号标记的字符串JVM会放入常量池
 *
 * @author LiJingTang
 * @date 2019-11-21 16:31
 */
public class StringTest {

    public static void main(String[] args) {
        // 字符串常量
        String constant01 = "String Constant Test";
        String constant02 = "String Constant Test";
        String intern = constant01.intern();
        System.out.println("constant01 == constant02 : " + (constant01 == constant02));
        System.out.println("constant01 == intern : " + (constant01 == intern));
        System.out.println("constant01和constant02取自具有唯一字符串的池 : " + (constant01.intern() == constant02.intern()));
        // 字符串常量相加
        String constantPlus01 = "String Constant Test" + " Plus";
        String constantPlus02 = "String Constant Test" + " Plus";
        // 编译器会优化都是常量会相加之后赋值，所以具有唯一字符串的池
        System.out.println("constantPlus01 == constantPlus01 : " + (constantPlus01 == constantPlus02));

        // 因为传入方法的是变量，编译器不能确定值，不会优化，所以不具有唯一字符串的池
        String constantPlus03 = splitString("String Constant Test", " Plus");
        String constantPlus04 = splitString("String Constant Test", " Plus");
        System.out.println("constantPlus03 == constantPlus04 : " + (constantPlus03 == constantPlus04));

        String constantValue = String.valueOf(intern);
        System.out.println("String.valueOf: " + (intern == constantValue));
        String str = new String(intern);
        System.out.println("new String: " + (intern == str));
    }

    private static String splitString(String value1, String value2) {
        return value1 + value2;
    }

    @Test
    public void testJoin() {
        System.out.println(String.join(",", "Hello", "World", "Bye"));
    }

    @Test
    public void testFormat() {
        Calendar c = new GregorianCalendar(1995, Calendar.MAY, 23);
        System.out.println(String.format("Duke's Birthday: %1$tY-%1$tm-%1$te", c));
    }

    /**
     * 比较两种方式性能
     */
    @Test
    public void testCaseCost() {
        int len = 100000;
        int[] array = new int[len];

        for (int i = 0; i < len; i++) {
            array[i] = i + 1;
        }

        long startTime = System.currentTimeMillis();
        String s = null;

        for (int i = 0; i < len; i++) {
            s = array[i] + "";
        }
        System.out.println(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            s = String.valueOf(array[i]);
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(s);
    }

}
