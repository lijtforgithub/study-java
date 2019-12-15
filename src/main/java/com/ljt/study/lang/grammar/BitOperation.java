package com.ljt.study.lang.grammar;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 位运算应用口诀	清零取反要用与，某位置一可用或 若要取反和交换，轻轻松松用异或
 *
 * @author LiJingTang
 * @date 2019-11-22 16:55
 */
public class BitOperation {
    /**
     * 移位  它们都是双目运算符，两个运算分量都是整型，结果也是整型。
     * 移位运算符就是在二进制的基础上对数字进行平移。按照平移的方向和填充数字的规则分为三种:<<（左移）、>>（带符号右移）和>>>（无符号右移）。
     * 在移位运算时，byte、short和char类型移位后的结果会变成int类型，对于byte、short、char和int进行移位时，
     * 规定实际移动的次数是移动次数和32的余数,也就是移位33次和移位1次得到的结果相同。
     * 移动long型的数值时，规定实际移动的次数是移动次数和64的余数，也就是移动66次和移动2次得到的结果相同。
     */
    @Test
    public void testShift() {
        /**
         * << 按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。
         * 数学意义：在数字没有溢出的前提下，对于正数和负数，左移一位都相当于乘以2的1次方，左移n位就相当于乘以2的n次方。
         */
        System.out.println(3 << 2);
        System.out.println(-3 << 2);

        // 0000 1100

        /**
         * >> 按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），高位的空位补符号位，即正数补零，负数补1。
         */
        System.out.println(12 >> 3);
        System.out.println(-2 >> 2);
        System.out.println(12 >> 5);

        /**
         * >>> 按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），高位的空位补零。对于正数来说和带符号右移相同，对于负数来说不同。
         */
        System.out.println(12 >>> 3);
        System.out.println(-12 >>> 3);
    }

    /**
     * 奇偶判断
     */
    @Test
    public void oddEven() {
        // x & 1 = 1 是奇数
        Predicate<Integer> odd = x -> (x & 1) == 1;
        // x & 1 = 0 是偶数
        Predicate<Integer> even = x -> (x & 1) == 0;
        System.out.println(odd.test(3));
        System.out.println(even.test(4));
    }

    /**
     * 求平均值（整除）
     * 有两个int类型变量x、y,首先要求x+y的和，再除以2，但是有可能x+y的结果会超过int的最大表示范围
     */
    @Test
    public void average() {
        BiFunction<Integer, Integer, Integer> fun = (x, y) -> (x & y) + ((x ^ y) >> 1);
        System.out.println(fun.apply(100, 110));
        System.out.println(fun.apply(1, 2));
    }

    /**
     * 对于一个大于0的整数，判断它是不是2的次方
     */
    @Test
    public void is2Pow() {
        Predicate<Integer> p = x -> ((x & (x - 1)) == 0) && (x != 0);
        System.out.println(p.test(8));
        System.out.println(p.test(10));
        System.out.println(p.test(16));
    }

    /**
     * 数值交换
     */
    @Test
    public void switchVal() {
        int x = 3, y = 4;
        x ^= y;
        y ^= x;
        x ^= y;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }

    /**
     * 求绝对值
     */
    @Test
    public void abs() {
        Function<Integer, Integer> abs = x -> {
            int y;
            y = x >> 31;
            return (x ^ y) - y;        // or: (x+y)^y
        };
        System.out.println(abs.apply(3));
        System.out.println(abs.apply(-4));
    }

    /**
     * 取模运算
     */
    @Test
    public void mod() {
        // a % (2^n) 等价于 a & (2^n - 1)
        BiFunction<Integer, Integer, Integer> m1 = (x, n) -> x % _2Pow(n);
        BiFunction<Integer, Integer, Integer> m2 = (x, n) -> x & (_2Pow(n) - 1);
        System.out.println(m1.apply(10, 4));
        System.out.println(m2.apply(10, 4));
    }

    /**
     * 2^n
     *
     * @param n
     * @return 2的n次方
     */
    private static int _2Pow(int n) {
        return (int) Math.pow(2, n);
    }

    /**
     * 乘法
     */
    @Test
    public void multi() {
        // a * (2^n) 等价于 a << n
        BiFunction<Integer, Integer, Integer> m1 = (x, n) -> x * _2Pow(n);
        BiFunction<Integer, Integer, Integer> m2 = (x, n) -> x << n;
        System.out.println(m1.apply(4, 4));
        System.out.println(m2.apply(4, 4));
    }

    /**
     * 除法
     */
    @Test
    public void div() {
        // a / (2^n) 等价于 a>> n
        BiFunction<Integer, Integer, Integer> d1 = (x, n) -> x / _2Pow(n);
        BiFunction<Integer, Integer, Integer> d2 = (x, n) -> x >> n;
        System.out.println(d1.apply(64, 4));
        System.out.println(d2.apply(64, 4));
    }

    /**
     * 求相反数
     */
    @Test
    public void opposite() {
        Function<Integer, Integer> opp = x -> (~x + 1);
        System.out.println(opp.apply(12));
        System.out.println(opp.apply(-10));
    }

    @Test
    public void mod2() {
        // a % 2 等价于 a & 1
        Function<Integer, Integer> m2 = x -> x & 1;
        System.out.println(m2.apply(5));
    }

}
