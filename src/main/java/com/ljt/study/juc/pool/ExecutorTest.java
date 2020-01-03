package com.ljt.study.juc.pool;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * @author LiJingTang
 * @date 2020-01-03 12:17
 */
public class ExecutorTest {

    public static void main(String[] args) {
        new DirectExecutor().execute(() -> System.out.println("DirectExecutor GO GO GO !!!"));
        new ThreadPerTaskExecutor().execute(() -> System.out.println("ThreadPerTaskExecutor GO GO GO !!!"));
    }

    // Executor 接口并没有严格地要求执行是异步的。在最简单的情况下，执行程序可以在调用者的线程中立即运行已提交的任务
    private static class DirectExecutor implements Executor {
        public void execute(Runnable r) {
            r.run(); // 方法调用 同步执行
        }
    }

    // 更常见的是，任务是在某个不是调用者线程的线程中执行的。以下执行程序将为每个任务生成一个新线程。
    private static class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    @Test
    public void testSerial() {
        SerialExecutor serialExecutor = new SerialExecutor(new DirectExecutor());

        serialExecutor.execute(() -> System.out.println("SerialExecutor 1 GO GO GO !!!"));
        serialExecutor.execute(() -> System.out.println("SerialExecutor 2 GO GO GO !!!"));
        serialExecutor.execute(() -> System.out.println("SerialExecutor 3 GO GO GO !!!"));
    }


    private static class SerialExecutor implements Executor {

        final Queue<Runnable> tasks = new ArrayDeque<>();
        final Executor executor;
        Runnable active;

        SerialExecutor(Executor executor) {
            this.executor = executor;
        }

        @Override
        public synchronized void execute(final Runnable r) {
            tasks.offer(() -> {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            });

            if (active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((active = tasks.poll()) != null) {
                executor.execute(active);
            }
        }
    }

}
