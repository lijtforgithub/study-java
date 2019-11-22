package com.ljt.study.lang.grammar;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 两种情况下finally语句是不会被执行
 * 1.try语句没有被执行到
 * 2.在try块中有System.exit(0);终止JVM
 *
 * @author LiJingTang
 * @date 2019-11-21 11:29
 */
public class TryCatchFinally {

    /**
     * finally语句是在try的return语句执行之后return返回之前执行
     */
    @Test
    public void testReturnExeTime() {
        System.out.println(tf());
    }

    /**
     * finally块中的return语句会覆盖try块中的return返回
     * 当发生异常catch中的return执行情况与未发生异常时try中return的执行情况完全一样
     */
    @Test
    public void testFinallyReturn() {
        System.out.println(tcf());
    }

    /**
     * 在finally语句中修改return语句的变量；那么原来的返回值可能因为finally里的修改而改变也可能不变
     * return语句的结果会赋给一个临时变量
     */
    @Test
    public void testUpdateReturnVar() {
        System.out.println(tfInt());
        System.out.println();
        // 地址传递
        System.out.println(tfMap());
    }

    /**
     * catch块抛出异常 finally语句块也会执行
     */
    @Test
    public void testCatchThrowFinally() {
        try {
            System.out.println("try 语句块");
            npe();
        } catch (Exception e) {
            System.out.println("catch 语句块");
            throw new RuntimeException("catch 语句块抛出转换异常");
        } finally {
            System.out.println("finally 语句块");
        }
    }


    private String tf() {
        try {
            System.out.println("try 语句块");
            return tr();
        } finally {
            System.out.println("finally 语句块");
        }
    }

    private String tr() {
        System.out.println("return 关键字后的方法");
        return "return 返回值";
    }


    private String tcf() {
        try {
            System.out.println("try 语句块");
            npe();
            return "try 返回值";
        } catch (Exception e) {
            System.out.println("catch 语句块");
            return "catch 返回值";
        } finally {
            System.out.println("finally 语句块");
            return "finally 返回值";
        }
    }

    private void npe() {
        throw new NullPointerException();
    }


    private int tfInt() {
        int i = 0;
        try {
            System.out.println("try 语句块");
            return i += 10;
        } finally {
            System.out.println("finally 语句块");
            System.out.println("i = " + i);
            i = 20;
            System.out.println("修改后i = " + i);
        }
    }

    private Map<String, String> tfMap() {
        Map<String, String> map = new HashMap<>();
        String key = "K";
        try {
            map.put(key, "try 语句块赋值");
            return map;
        } finally {
            map.put(key, "finally 语句块赋值");
            map = null;
        }
    }

}
