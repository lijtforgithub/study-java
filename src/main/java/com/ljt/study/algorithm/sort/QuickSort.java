package com.ljt.study.algorithm.sort;

/**
 * 快速排序（不稳定）
 *
 * @author LiJingTang
 * @date 2019-12-29 20:55
 */
public class QuickSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            quickSort(array, 0, array.length - 1);
        }
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int key = array[low];
            int i = low;
            int j = high;

            while (i != j) {
                while (i < j && array[j] >= key) { // 按j--方向遍历目标数组，直到比key小的值为止
                    j--;
                }
                if (i < j) {
                    array[i] = array[j]; // array[i]已经保存在key中，可将后面的数填入
                    i++;
                }

                while (i < j && array[i] <= key) { // 此处一定要小于等于，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次
                    i++;
                }
                if (i < j) {
                    array[j] = array[i]; // array[j]已保存在array[i]中，可将前面的值填入
                    j--;
                }
            }

            array[i] = key; // 此时i==j
            quickSort(array, low, i - 1);
            quickSort(array, i + 1, high);
        }
    }

}
