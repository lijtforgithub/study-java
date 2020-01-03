package com.ljt.study.juc.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:29
 */
public class RecursiveTaskTest {

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkjoinPool = new ForkJoinPool();
        CalcRecursiveTask task = new CalcRecursiveTask(1, 100); // 生成一个计算任务，计算1+2+3+4
        Future<Integer> result = forkjoinPool.submit(task); // 执行一个任务
        System.out.println(result.get());
    }

    private static class CalcRecursiveTask extends RecursiveTask<Integer> {
        private static final long serialVersionUID = -3611254198265061729L;

        public static final int threshold = 2;
        private int start;
        private int end;

        public CalcRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;

            // 如果任务足够小就计算任务
            boolean canCompute = (end - start) <= threshold;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // 如果任务大于阈值，就分裂成两个子任务计算
                int middle = (start + end) / 2;
                CalcRecursiveTask leftTask = new CalcRecursiveTask(start, middle);
                CalcRecursiveTask rightTask = new CalcRecursiveTask(middle + 1, end);

                // 执行子任务
                leftTask.fork();
                rightTask.fork();

                // 等待任务执行结束合并其结果
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();

                // 合并子任务
                sum = leftResult + rightResult;
            }

            return sum;
        }
    }

}
