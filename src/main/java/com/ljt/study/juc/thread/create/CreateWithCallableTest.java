package com.ljt.study.juc.thread.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author LiJingTang
 * @date 2024-03-21 10:17
 */
class CreateWithCallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 同步非阻塞
        FutureTask futureTask = new FutureTask(new CallTask());
        Thread t = new Thread(futureTask, "future-task");
        t.start();
        System.out.println("不阻塞主线程");
        System.out.println("同步获取子线程结果 " + futureTask.get());
    }

    private static class CallTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            System.out.println(Thread.currentThread().getName() + " " + sum);
            return sum;
        }
    }

}
