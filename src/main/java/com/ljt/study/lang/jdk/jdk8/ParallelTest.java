package com.ljt.study.lang.jdk.jdk8;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:32
 */
public class ParallelTest {

    public static void main(String[] args) {
        long[] arrayOfLong = new long[20000];

        Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
        Arrays.stream(arrayOfLong).limit(10)
                .forEach(i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arrayOfLong);
        Arrays.stream(arrayOfLong).limit(10)
                .forEach(i -> System.out.print(i + " "));
    }

}
