package com.ljt.study.juc.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author LiJingTang
 * @date 2020-01-03 08:36
 */
class ThreadLocalTest {

    @BeforeEach
    void beforeEach() {
        System.out.println("主线程是：" + Thread.currentThread().getName());
    }

    private void set(ThreadLocal<String> threadLocal) {
        threadLocal.set("我是" + Thread.currentThread().getName() + "的值");
    }

    @SneakyThrows
    @Test
    void threadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        set(threadLocal);
        CountDownLatch latch = new CountDownLatch(2);

        final Thread getThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取不到父线程和其他子线程的值：" + threadLocal.get());
            latch.countDown();
        });
        getThread.setName("get 子线程");

        Thread setThread = new Thread(() -> {
            threadLocal.set("自己设置自己获取");
            System.out.println(Thread.currentThread().getName() + "设置结束：" + threadLocal.get());
            latch.countDown();
            System.out.println(getThread.getName() + "开始");
            getThread.start();
        });
        setThread.setName("set 子线程");
        setThread.start();

        latch.await();
        System.out.println(Thread.currentThread().getName() + " 线程可以获取到值：" + threadLocal.get());
    }


    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(4);
        ((ThreadPoolExecutor) executor).prestartAllCoreThreads();
    }

    @SneakyThrows
    @Test
    void inheritableThreadLocal() {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        set(threadLocal);
        CountDownLatch latch = new CountDownLatch(5);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "可以获取到父线程的值：" + threadLocal.get());
            latch.countDown();
        }).start();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        ((ThreadPoolExecutor) executorService).prestartAllCoreThreads();

        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 方法内部的线程池可以获取到主线程的值：" + threadLocal.get());
                latch.countDown();
            });
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 方法外部预热好的线程池获取不到主线程的值：" + threadLocal.get());
                latch.countDown();
            });
        }

        latch.await();
    }


    /**
     * https://github.com/alibaba/transmittable-thread-local
     */

    @SneakyThrows
    @Test
    void transmittableThreadLocal() {
        ThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
        set(threadLocal);
        CountDownLatch latch = new CountDownLatch(1);
        // 包装线程池
        executor = TtlExecutors.getTtlExecutorService(executor);

        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 阿里插件线程池获取主线程的值：" + threadLocal.get());
            latch.countDown();
        });

        latch.await();
    }


    @SneakyThrows
    @Test
    void ttlAgent() {
        TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

        set(threadLocal);
        CountDownLatch latch = new CountDownLatch(1);

        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 阿里插件线程池获取主线程的值：" + threadLocal.get());
            latch.countDown();
        });

        latch.await();
    }

}
