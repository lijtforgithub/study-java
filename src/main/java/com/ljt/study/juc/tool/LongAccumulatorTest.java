package com.ljt.study.juc.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2025-07-15 14:13
 */
public class LongAccumulatorTest {

    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);

        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));
        Thread.sleep(2000);

        System.out.println(accumulator.getThenReset());
    }

}
