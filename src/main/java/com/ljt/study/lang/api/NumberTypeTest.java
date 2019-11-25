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
        System.out.println(Byte.decode("012"));    // 八进制
        System.out.println(Byte.decode("0xF"));    // 十六进制
        System.out.println(Byte.decode("+#E"));    // 十六进制

        System.out.println(Byte.parseByte("101011", 2)); // 二进制
        System.out.println(Byte.parseByte("10", 8)); // 八进制
        System.out.println(Byte.parseByte("10")); // 十进制
        System.out.println(Byte.parseByte("0E", 16)); // 十六进制

        byte min = -128;
        byte max = 127;

        System.out.println(min == Byte.MIN_VALUE);
        System.out.println(max == Byte.MAX_VALUE);
        System.out.println(Integer.toBinaryString(min));
        System.out.println(Integer.toBinaryString(max));

        byte a = -1;
        byte b = 1;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));

        System.out.println(Byte.SIZE);
        System.out.println(Byte.BYTES);
    }

    @Test
    public void testShort() {
        System.out.println(Short.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);

        short a = -1;
        short b = 1;

        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(127));
        System.out.println(Integer.toBinaryString(-128));
        System.out.println(Short.SIZE);
        System.out.println(Short.BYTES);
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

        int a = 0b10000000_00000000_00000000_0000001;
        int b = 0b00000001;
        int c = 1_2_3;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));

        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.SIZE);
        System.out.println(Integer.BYTES);
    }

    @Test
    public void testLong() {
        long a = -1;
        long b = 0L;

        System.out.println(Long.toBinaryString(a));
        System.out.println(Long.toBinaryString(b));

        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.SIZE);
        System.out.println(Long.BYTES);
    }

    @Test
    public void testFloat() {
        System.out.println(Float.floatToIntBits(3.6F));

        float f = 0.1F;
        double d = 0.1D;
        double val = 1.0 / 10;

        System.out.println(f == d);
        System.out.println(f == val);
        System.out.println(d == val);

        System.out.println(Double.toHexString(d));

        System.out.println(d / 0);
    }

    @Test
    public void testChar() {
        char a = 'a';

        System.out.println(a);
        System.out.println((int) a);

        System.out.println((char) 0);
    }

    @Test
    public void testBoolean() {
        System.out.println(Boolean.FALSE);
    }

    @Test
    public void testBox() {
        Integer i11 = 12;
        Integer i12 = 12;
        /**
         * 一个字节-128 - 127 会被缓存起来
         */
        System.out.println(i11 == i12);
        Integer i13 = Integer.valueOf(10);
        Integer i14 = Integer.valueOf(10);
        System.out.println(i13 == i14);
        Integer i10 = Integer.valueOf(12);
        System.out.println(i10 == i11);

        /**
         * new创建新的对象
         */
        Integer i15 = new Integer(78);
        Integer i16 = new Integer(78);
        System.out.println(i15 == i16);

        Integer i21 = -129;
        Integer i22 = -129;
        System.out.println(i21 == i22);
    }

    /**
     * 整数运算的溢出：两个整数进行运算时，其结果可能会超过整数的范围而溢出。
     * 正数过大而产生的溢出，结果为负值；负整数过大而产生的溢出，结果为正数。
     */
    @Test
    public void testOverflow() {
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        System.out.println(max);
        System.out.println(min);

        max = max + 1;
        min = min - 1;
        System.out.println(max);
        System.out.println(min);
    }

}
