package com.ljt.study.juc.pool;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.ljt.study.juc.thread.ThreadMxBeanTest.printJvmThread;

/**
 * @author LiJingTang
 * @date 2025-07-26 22:49
 */
public class ScheduledExecutorServiceTest {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("tt11");
                return thread;
            }
        });
        scheduledExecutorService.schedule(() -> System.out.println("world"), 10, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> System.out.println("hello"), 5, TimeUnit.SECONDS);


        scheduledExecutorService.shutdown();

        TimeUnit.SECONDS.sleep(15);

        printJvmThread();
    }

}
