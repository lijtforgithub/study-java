package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:30
 */
public class NumberTypeTest {

    @Test
    public void testByte() {
        System.out.println(Byte.decode("-0101")); // 八进制
        System.out.println(Byte.decode("012"));	// 八进制
        System.out.println(Byte.decode("0xF"));	// 十六进制
        System.out.println(Byte.decode("+#E"));	// 十六进制

        System.out.println(Byte.parseByte("101011", 2)); // 二进制
        System.out.println(Byte.parseByte("10", 8)); // 八进制
        System.out.println(Byte.parseByte("10")); // 十进制
        System.out.println(Byte.parseByte("0E", 16)); // 十六进制
    }

    @Test
    public void testInteger() {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.toString(Integer.MIN_VALUE, 2));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));

        System.out.println(Integer.bitCount(5));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.bitCount(-5));

        System.out.println(Integer.rotateLeft(2, 2));
        System.out.println(Integer.rotateLeft(8, -2));
        System.out.println(Integer.rotateRight(2, -2));

        System.out.println(Integer.getInteger("os"));
    }

    @Test
    public void testFloat() {
        System.out.println(Float.floatToIntBits(3.6F));
    }

}
