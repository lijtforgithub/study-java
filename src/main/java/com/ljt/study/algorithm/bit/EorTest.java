package com.ljt.study.algorithm.bit;

import org.junit.jupiter.api.Test;

import java.util.function.IntUnaryOperator;

/**
 * 异或：不进位的相加
 * 异或预算满足交换律和结合律
 * N^N = 0
 * 0^N = N
 *
 * @author LiJingTang
 * @date 2021-07-12 11:31
 */
class EorTest {

    @Test
    void swap() {
        // a 和 b 可以值相同 但是不能指向同一块内存 例如同为数组的一个下标值
        int a = 100;
        int b = 10;

        a = a ^ b;  // a = 100 ^ 10
        b = a ^ b;  // b = 100 ^ 10 ^ 10 = 100
        a = a ^ b;  // a = 100 ^ 10 ^ 100 = 10

        System.out.println(a);
        System.out.println(b);
    }

    /**
     * 最低位的 1
     */
    @Test
    void last1() {
        IntUnaryOperator fun = n -> n & (~n + 1);

        System.out.println(fun.applyAsInt(12));
        System.out.println(fun.applyAsInt(7));
    }

    /**
     * 1 出现次数
     */
    @Test
    void count1() {
        IntUnaryOperator fun = n -> {
            int count = 0;
            while (n != 0) {
                int rightOne = n & (~n + 1);
                // 消掉最地位的1
                n ^= rightOne;
                count++;
            }
            return count;
        };

        System.out.println(fun.applyAsInt(7));
    }


    /**
     * 一组整数 只有一个数出现了奇数次 其他数出现偶数次
     */
    @Test
    void oddTimesNum1() {
        int[] array = new int[]{1, 1, 4, 5, 5, 3, 3};
        int eor = 0;
        for (int v : array) {
            eor ^= v;
        }

        System.out.println(eor);
    }

    /**
     * 一组整数 两个数出现了奇数次 其他数出现偶数次
     */
    @Test
    void oddTimesNum2() {
        int[] array = new int[]{1, 1, 4, 5, 5, 3, 3, 9};
        int eor = 0;
        for (int v : array) {
            eor ^= v;
        }

        // 两个数必然有一个数在此低位为 1 另一个是 0
        int rightOne = eor & (~eor + 1);
        int onlyOne = 0;

        for (int v : array) {
            // 筛选出此低位为 1的数 因为偶数次出现的数要么此低位为1 要么为0 但是是偶数次 所以异或之后都为0 最后的值就是两个奇数次出现中的此低位为1的数
            if ((v & rightOne) != 0) {
                onlyOne ^= v;
            }
        }

        System.out.println(onlyOne + " " + (onlyOne ^ eor));
    }

}
