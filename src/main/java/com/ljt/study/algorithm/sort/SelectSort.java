package com.ljt.study.algorithm.sort;

/**
 * 选择排序（不稳定）
 * 每一次从待排序的数据元素中选出最小的一个元素，存放在序列的起始位置，直到全部待排序的数据元素排完
 *
 * @author LiJingTang
 * @date 2019-12-29 21:01
 */
public class SelectSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            for (int i = 0; i < array.length; i++) {
                int minIndex = i;

                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }

                if (minIndex != i) {
                    int temp = array[minIndex];
                    array[minIndex] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

}
