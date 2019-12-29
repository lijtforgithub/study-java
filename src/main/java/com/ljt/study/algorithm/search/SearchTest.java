package com.ljt.study.algorithm.search;

import com.ljt.study.algorithm.sort.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-12-29 20:50
 */
public class SearchTest {

    private int[] array = {8, 9, 7, 1, 3, 2, 5, 6, 7, 4};
    private int target = 5;

    @Test
    public void testOrder() {
        System.out.println(orderSearch(array, target));
    }

    @Test
    public void testBinary() {
        QuickSort.sort(array);
        System.out.println(Arrays.toString(array));
        System.out.println(binarySearch(array, target));
    }

    /**
     * 二分/折半查找（有序）
     *
     * @param array 有序
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        if (array != null) {
            int low = 0;
            int high = array.length - 1;

            while ((low <= high) && (low <= array.length - 1) && (high <= array.length - 1)) {
                int middle = (high + low) / 2;

                if (target == array[middle]) {
                    return middle;
                } else if (target < array[middle]) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            }
        }

        return -1;
    }

    /**
     * 顺序查找（无序、有序）
     *
     * @param array 无序、有序
     * @param target
     * @return
     */
    public static int orderSearch(int[] array, int target) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (target == array[i])
                    return i;
            }
        }

        return -1;
    }

}
