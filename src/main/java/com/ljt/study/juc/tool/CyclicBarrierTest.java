package com.ljt.study.juc.tool;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 栅栏
 *
 * @author LiJingTang
 * @date 2020-01-03 10:40
 */
class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int num = 3;
        final CyclicBarrier barrier = new CyclicBarrier(num);

        Function<CyclicBarrier, String> fun = cb -> (cb.getNumberWaiting() + 1) + " 个已经到达，"
                + (num == cb.getNumberWaiting() + 1 ? "都到齐了，继续走啊" : "正在等候");

        for (int i = 0; i < num; i++) {
            executor.execute(() -> {
                try {
                    ThreadUtils.sleepSeconds(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " 即将到达集合地点1，当前已有 " + fun.apply(barrier));
                    barrier.await();

                    ThreadUtils.sleepSeconds(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " 即将到达集合地点2，当前已有 " + fun.apply(barrier));
                    // 可以再次使用 循环障碍
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }

    @Test
    void usePhaser() {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int num = 3;
        final Phaser phaser = new Phaser(num);

        Supplier<String> supplier = () -> (phaser.getArrivedParties() + 1) + " 个已经到达，"
                + (num == phaser.getArrivedParties() + 1 ? "都到齐了，继续走啊" : "正在等候");

        for (int i = 0; i < num; i++) {
            executor.execute(() -> {
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " 即将到达集合地点1，当前已有" + supplier.get());
                phaser.arriveAndAwaitAdvance();

                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " 即将到达集合地点2，当前已有" + supplier.get());
                phaser.arriveAndAwaitAdvance();
            });
        }

        executor.shutdown();

        // 测试线程会提前结束
        ThreadUtils.sleepSeconds(30);
    }

}
