package com.ljt.study.algorithm.sort;

/**
 * 插入排序（稳定）
 * 基本操作就是将一个数据插入到已经排好序的有序数据中，从而得到一个新的、个数加一的有序数据
 *
 * @author LiJingTang
 * @date 2019-12-29 20:58
 */
public class InsertSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            for (int i = 1; i < array.length; i++) {
                for (int j = i; j > 0; j--) {
                    if (array[j] < array[j - 1]) {
                        int temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                    }
                }
            }
        }
    }

}
