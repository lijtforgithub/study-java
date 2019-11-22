package com.ljt.study.lang.grammar;

import org.junit.jupiter.api.Test;

/**
 * 位运算应用口诀	清零取反要用与，某位置一可用或 若要取反和交换，轻轻松松用异或
 *
 * @author LiJingTang
 * @date 2019-11-22 16:55
 */
public class BitOperation {
    /**
     * 移位  它们都是双目运算符，两个运算分量都是整型，结果也是整型。
     *
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

}
