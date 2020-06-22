package com.ljt.study.juc.aqs;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * 栅栏
 *
 * @author LiJingTang
 * @date 2020-01-03 10:40
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            Runnable command = () -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点1，当前已有"
                            + (cb.getNumberWaiting() + 1) + "个已经到达，" + (2 == cb.getNumberWaiting() ? "都到齐了，继续走啊" : "正在等候"));
                    cb.await();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点2，当前已有"
                            + (cb.getNumberWaiting() + 1) + "个已经到达，" + (2 == cb.getNumberWaiting() ? "都到齐了，继续走啊" : "正在等候"));
                    cb.await();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点3，当前已有"
                            + (cb.getNumberWaiting() + 1) + "个已经到达，" + (2 == cb.getNumberWaiting() ? "都到齐了，继续走啊" : "正在等候"));
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.execute(command);
        }

        service.shutdown();
    }

    @Test
    public void testPhaser() {
        ExecutorService service = Executors.newCachedThreadPool();
        final Phaser phaser = new Phaser(3);

        for (int i = 0; i < 3; i++) {
            Runnable command = () -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点1，当前已有"
                            + (phaser.getArrivedParties() + 1) + "个已经到达，" + (2 == phaser.getArrivedParties() ? "都到齐了，继续走啊" : "正在等候"));
                    phaser.arriveAndAwaitAdvance();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点2，当前已有"
                            + (phaser.getArrivedParties() + 1) + "个已经到达，" + (2 == phaser.getArrivedParties() ? "都到齐了，继续走啊" : "正在等候"));
                    phaser.arriveAndAwaitAdvance();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点3，当前已有"
                            + (phaser.getArrivedParties() + 1) + "个已经到达，" + (2 == phaser.getArrivedParties() ? "都到齐了，继续走啊" : "正在等候"));
                    phaser.arriveAndAwaitAdvance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.execute(command);
        }

        service.shutdown();
        ThreadUtils.sleepSeconds(30);
    }

}
