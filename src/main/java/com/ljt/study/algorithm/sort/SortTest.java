package com.ljt.study.algorithm.sort;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-12-29 21:05
 */
public class SortTest {

    private int[] array = {8, 9, 7, 1, 3, 2, 5, 6, 7, 4};
    private long startTime;

    @BeforeEach
    public void before() {
        startTime = System.nanoTime();
    }

    @AfterEach
    public void after() {
        System.out.println("耗时 " + (System.nanoTime() - startTime));
        System.out.println(Arrays.toString(array));
    }


    @Test
    public void testBubble() {
        BubbleSort.sort(array);
    }

    @Test
    public void testQuick() {
        QuickSort.sort(array);
    }

    @Test
    public void testMerage() {
        MergeSort.sort(array);
    }

    @Test
    public void testInsert() {
        InsertSort.sort(array);
    }

    @Test
    public void testSelect() {
        SelectSort.sort(array);
    }

    @Test
    public void testShell() {
        ShellSort.sort(array);
    }

    @Test
    public void testHeap() {
        HeapSort.sort(array);
    }

}
