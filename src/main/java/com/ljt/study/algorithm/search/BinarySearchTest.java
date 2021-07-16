package com.ljt.study.algorithm.search;

import org.junit.jupiter.api.Test;

/**
 * 二分/折半查找
 *
 * @author LiJingTang
 * @date 2019-12-29 20:50
 */
class BinarySearchTest {

    @Test
    void test() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        System.out.println(binary(array, 3));
        System.out.println(nearLeft(array, 3));
        System.out.println(nearRight(array, 3));
        System.out.println(lessIndex(new int[] {10, 9, 8, 9, 10, 7, 8, 6, 7}));
    }

    /**
     * 有序数组 二分查找
     */
    private int binary(int[] array, int value) {
        if (array != null) {
            int low = 0;
            int high = array.length - 1;
            int mid;

            while (low <= high) {
                // 这种写法可以防止溢出 等同于 mid = (low + high) / 2
                mid = low + ((high - low) >> 1);

                if (value == array[mid]) {
                    return mid;
                } else if (value < array[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    /**
     * 找满足>=value的最左位置
     */
    private int nearLeft(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] >= value) {
                index = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return index;
    }

    /**
     * 找满足<=value的最右位置
     */
    private int nearRight(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] <= value) {
                index = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return index;
    }

    /**
     * 相邻的俩个数不等 局部最小
     */
    private int lessIndex(int[] array) {
        if (array == null || array.length == 0) {
            return -1; // no exist
        }
        if (array.length == 1 || array[0] < array[1]) {
            return 0;
        }
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }

        int left = 1;
        int right = array.length - 2;

        while (left < right) {
            int mid = (left + right) / 2;
            if (array[mid] > array[mid - 1]) {
                right = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

}
