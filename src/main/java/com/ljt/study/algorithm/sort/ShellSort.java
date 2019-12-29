package com.ljt.study.algorithm.sort;

/**
 * 希尔排序（不稳定）
 *
 * @author LiJingTang
 * @date 2019-12-29 21:02
 */
public class ShellSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            int temp;
            for (int k = array.length / 2; k > 0; k /= 2) {
                for (int i = k; i < array.length; i++) {
                    for (int j = i; j >= k; j -= k) {
                        if (array[j - k] > array[j]) {
                            temp = array[j - k];
                            array[j - k] = array[j];
                            array[j] = temp;
                        }
                    }
                }
            }
        }
    }

}
