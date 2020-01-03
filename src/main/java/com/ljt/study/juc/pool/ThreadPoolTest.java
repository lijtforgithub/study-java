package com.ljt.study.juc.pool;

import com.ljt.study.juc.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2020-01-03 13:02
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService threadPool = null;
//		threadPool = Executors.newFixedThreadPool(3); // 创建固定大小的线程池
//		threadPool = Executors.newCachedThreadPool(); // 创建缓存线程池
        threadPool = Executors.newSingleThreadExecutor(); // 创建单一线程池(如何实现线程死掉后重新启动)如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程将代替它执行后续的任务。

        for (int i = 1; i <= 10; i++) {
            final int task = i;

            threadPool.execute(() -> {
                for (int j = 1; j <= 10; j++) {
                    ThreadUtils.sleep(50, TimeUnit.MILLISECONDS);
                    System.out.println(Thread.currentThread().getName() + " is loopf of " + j + " for task of " + task);
                }
            });
        }

        System.out.println("all of 10 tasks have committed!");
        threadPool.shutdown();
//		threadPool.shutdownNow();

        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(() -> System.out.println("bombing"), 10, 2, TimeUnit.SECONDS);
    }

}
