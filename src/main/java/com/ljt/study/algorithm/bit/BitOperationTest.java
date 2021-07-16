package com.ljt.study.algorithm.bit;

import org.junit.jupiter.api.Test;

import java.util.function.*;

/**
 * @author LiJingTang
 * @date 2019-11-22 16:55
 */
class BitOperationTest {

    /**
     * 移位 双目运算符 移位运算符就是在二进制的基础上对数字进行平移
     * <<（左移）、>>（带符号右移：符号位根据正负补0或1）和>>>（无符号右移：高位都补0）
     * byte、short和char类型移位后的结果会变成int类型
     * 对于byte、short、char和int进行移位时 规定实际移动的次数是移动次数和32的余数 也就是移位33次和移位1次得到的结果相同
     * 移动long型的数值时 规定实际移动的次数是移动次数和64的余数 也就是移动66次和移动2次得到的结果相同
     */
    @Test
    void testShift() {
        /*
         * << 按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。
         * 数学意义：在数字没有溢出的前提下，对于正数和负数，左移一位都相当于乘以2的1次方，左移n位就相当于乘以2的n次方。
         */
        System.out.println(3 << 2);
        System.out.println(-3 << 2);

        // 0000 1100

        /*
         * >> 按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），高位的空位补符号位，即正数补零，负数补1。
         */
        System.out.println(12 >> 3);
        System.out.println(-2 >> 2);
        System.out.println(12 >> 5);

        /*
         * >>> 按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），高位的空位补零。对于正数来说和带符号右移相同，对于负数来说不同。
         */
        System.out.println(12 >>> 3);
        System.out.println(-12 >>> 3);
    }


    /**
     * 奇偶判断
     */
    @Test
    void oddOrEven() {
        // x & 1 = 1 是奇数
        Predicate<Integer> odd = x -> (x & 1) == 1;
        // x & 1 = 0 是偶数
        Predicate<Integer> even = x -> (x & 1) == 0;
        System.out.println(odd.test(3));
        System.out.println(even.test(4));
    }

    /**
     * 取模运算
     * a % (2^n) 等价于 a & (2^n - 1)
     */
    @Test
    void mod() {
        IntBinaryOperator fun1 = (x, n) -> x % _2Pow(n);
        IntBinaryOperator fun2 = (x, n) -> x & (_2Pow(n) - 1);

        System.out.println(fun1.applyAsInt(10, 4));
        System.out.println(fun2.applyAsInt(10, 4));
    }

    /**
     * a % 2 等价于 a & 1
     */
    @Test
    void mod2() {
        IntUnaryOperator fun = n -> n & 1;

        System.out.println(fun.applyAsInt(5));
    }

    /**
     * 2^n
     */
    private int _2Pow(int n) {
        return (int) Math.pow(2, n);
    }

    /**
     * 乘法
     * a * (2^n) 等价于 a << n
     */
    @Test
    void multi() {
        IntBinaryOperator fun1 = (x, n) -> x * _2Pow(n);
        IntBinaryOperator fun2 = (x, n) -> x << n;

        System.out.println(fun1.applyAsInt(4, 4));
        System.out.println(fun2.applyAsInt(4, 4));

        System.out.println((3 * 2 + 1) == (3 << 1 | 1));
    }

    /**
     * 除法
     * a / (2^n) 等价于 a>> n
     */
    @Test
    void div() {
        IntBinaryOperator fun1 = (x, n) -> x / _2Pow(n);
        IntBinaryOperator fun2 = (x, n) -> x >> n;

        System.out.println(fun1.applyAsInt(64, 4));
        System.out.println(fun2.applyAsInt(64, 4));
    }

    /**
     * 求相反数
     */
    @Test
    void opposite() {
        IntUnaryOperator fun = n -> (~n + 1);

        System.out.println(fun.applyAsInt(12));
        System.out.println(fun.applyAsInt(-10));
    }

    /**
     * 求平均值（整除）
     * 有两个int类型变量x、y,首先要求x+y的和，再除以2，但是有可能x+y的结果会超过int的最大表示范围
     */
    @Test
    void average() {
        IntBinaryOperator fun = (x, y) -> (x & y) + ((x ^ y) >> 1);

        System.out.println(fun.applyAsInt(100, 110));
        System.out.println(fun.applyAsInt(1, 2));
    }

    /**
     * 对于一个大于0的整数，判断它是不是2的次方
     */
    @Test
    void is2Pow() {
        IntPredicate p = x -> ((x & (x - 1)) == 0) && (x != 0);

        System.out.println(p.test(8));
        System.out.println(p.test(10));
        System.out.println(p.test(16));
    }

    /**
     * 求绝对值
     */
    @Test
    void abs() {
        IntUnaryOperator abs = x -> {
            int y;
            y = x >> 31;
            return (x ^ y) - y;        // or: (x+y)^y
        };

        System.out.println(abs.applyAsInt(3));
        System.out.println(abs.applyAsInt(-4));
    }

}
