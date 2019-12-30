package com.ljt.study.algorithm.sort;

/**
 * 归并排序（稳定）
 *
 * @author LiJingTang
 * @date 2019-12-29 20:59
 */
public class MergeSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            mergeSort(array, 1);
        }
    }

    /**
     * @param gap 分割长度
     */
    public static void mergeSort(int[] array, int gap) {
        if (gap < array.length) {
            int i = 0;

            for (i = 0; i + 2 * gap - 1 < array.length; i = i + 2 * gap) { // 归并两个相邻子表
                merge(array, i, i + gap - 1, i + 2 * gap - 1);
            }

            if (i + gap - 1 < array.length) { // 余下两个子表，后者长度小于gap
                merge(array, i, i + gap - 1, array.length - 1);
            }

            mergeSort(array, gap * 2);
        }
    }

    /**
     * 合并两个有序的序列
     */
    private static void merge(int[] array, int low, int mid, int high) {
        int i = low; // 第一段序列的下标
        int j = mid + 1; // 第二段序列的下标
        int k = 0; // 临时数组的下标
        int[] tempArray = new int[high - low + 1]; // 临时合并数组

        while (i <= mid && j <= high) { // 扫描第一段和第二段序列，直到有一个扫描结束
            if (array[i] <= array[j]) { // 判断第一段和第二段取出的数哪个更小，将其存入合并序列，并继续向下扫描
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }
        // 总有一个先扫描完
        while (i <= mid) { // 若第一段序列还没扫描完，将其全部复制到临时数组
            tempArray[k++] = array[i++];
        }
        while (j <= high) { // 若第二段序列还没扫描完，将其全部复制到临时数组
            tempArray[k++] = array[j++];
        }

        System.arraycopy(tempArray, 0, array, low, tempArray.length); // 将合并数组复制到原始序列中
    }

}
