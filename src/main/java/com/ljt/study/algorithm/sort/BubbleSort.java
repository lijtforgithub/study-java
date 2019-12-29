package com.ljt.study.algorithm.sort;

/**
 * 冒泡排序（稳定）
 *
 * @author LiJingTang
 * @date 2019-12-29 20:57
 */
public class BubbleSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            int temp = 0;
            for (int i = array.length - 1; i > 0; --i) {
                for (int j = 0; j < i; ++j) {
                    if (array[j + 1] < array[j]) {
                        temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }

}
