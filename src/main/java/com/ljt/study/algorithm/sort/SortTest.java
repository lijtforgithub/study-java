package com.ljt.study.algorithm.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-12-29 21:05
 */
class SortTest {

    private static final int[] ARRAY = {8, 9, 7, 1, 3, 2, 5, 6, 7, 4};

    @Test
    void sort() {
        insertion(ARRAY);
        System.out.println(Arrays.toString(ARRAY));
    }

    /**
     * 冒泡排序（稳定）
     * 如果左边的元素大于右边的元素 交换位置
     * 0~n
     * 0~n-1
     * 0~n-2
     */
    private void bubble(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 选择排序（不稳定）
     * 每次从待排序的数据元素中选出最小的一个元素，存放在序列的起始位置，直到全部待排序的数据元素排完
     */
    private void selection(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i; // 最小值下标
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(array, i, min);
            }
        }
    }

    /**
     * 插入排序（稳定）
     * 基本操作就是将一个数据插入到已经排好序的有序数据中，从而得到一个新的、个数加一的有序数据
     */
    private void insertion(int[] array) {
        // 0~0 有序的
        // 0~i 想有序
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                }
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }

}
