package com.ljt.study.algorithm.sort;

/**
 * 堆排序（不稳定）
 *
 * @author LiJingTang
 * @date 2019-12-29 21:03
 */
public class HeapSort {

    public static void sort(int[] array) {
        if (array != null && array.length > 1) {
            for (int i = 1; i < array.length; i++) {
                makeHeap(array, i);
            }

            for (int i = array.length - 1; i > 0; i--) {
                int temp = array[i];
                array[i] = array[0];
                array[0] = temp;
                rebuildHeap(array, i);
            }
        }
    }

    private static void makeHeap(int[] array, int index) {
        while (index > 0 && array[index] > array[(index - 1) / 2]) {
            int temp = array[index];
            array[index] = array[(index - 1) / 2];
            array[(index - 1) / 2] = temp;
            index = (index - 1) / 2;
        }
    }

    private static void rebuildHeap(int[] array, int size) {
        int currentIndex = 0;
        int right = currentIndex * 2 + 2;
        int left = currentIndex * 2 + 1;
        int maxIndex = currentIndex;
        boolean isHeap = false;

        while (!isHeap) {
            if (left < size && array[currentIndex] < array[left]) {
                maxIndex = left;
            }
            if (right < size && array[maxIndex] < array[right]) {
                maxIndex = right;
            }
            if (currentIndex == maxIndex) {
                isHeap = true;
            } else {
                int temp = array[currentIndex];
                array[currentIndex] = array[maxIndex];
                array[maxIndex] = temp;
                currentIndex = maxIndex;
                right = currentIndex * 2 + 2;
                left = currentIndex * 2 + 1;
            }
        }
    }

}
