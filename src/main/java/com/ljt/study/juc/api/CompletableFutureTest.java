package com.ljt.study.juc.api;

import com.ljt.study.juc.ThreadUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author jtli3
 * @date 2022-05-06 11:17
 */
class CompletableFutureTest {

    private static final Random RANDOM = new Random();

    @SneakyThrows
    @Test
    void getNow() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(5);
            return new Value(1);
        });

        System.out.println(future.getNow(new Value(0)));
    }

    @SneakyThrows
    @Test
    void get() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(5);
            return new Value(1);
        });

        System.out.println(future.get(2, TimeUnit.SECONDS));
    }

    @SneakyThrows
    @Test
    void complete() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(10);

            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        new Thread(() -> {
            ThreadUtils.sleepSeconds(3);

            future.complete(new Value(2));
            System.out.println(Thread.currentThread().getName() + " : " + 2);
        }).start();

        System.out.println(future.get());
    }

    @SneakyThrows
    @Test
    void completedFuture() {
        CompletableFuture<Value> future = CompletableFuture.completedFuture(new Value(1));
        System.out.println(future.get());
    }

    @SneakyThrows
    @Test
    void allOf() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(2);
        });

        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2);
        future.get();
        System.out.println("end");
    }

    @SneakyThrows
    @Test
    void anyOf() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(2);
        });

        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2);
        System.out.println("end：" + future.get());
    }

    @SneakyThrows
    @Test
    void thenApply() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        IntStream.rangeClosed(2, 5).forEach(i -> CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            ThreadUtils.sleepSeconds(10);
        }));

        // 用的同一个线程
        CompletableFuture<Value> future2 = future1.thenApply(v -> {
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(v.value * 10);
        });

        System.out.println(future2.get());
    }

    @SneakyThrows
    @Test
    void thenApplyAsync() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        IntStream.rangeClosed(2, 5).forEach(i -> CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            ThreadUtils.sleepSeconds(10);
        }));

        // 用的不同线程 也可能同一个
        CompletableFuture<Value> future2 = future1.thenApplyAsync(v -> {
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(v.value * 10);
        });

        System.out.println(future2.get());
    }

    @SneakyThrows
    @Test
    void thenCombine() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(2);
        });

        CompletableFuture<Value> future = future1.thenCombine(future2, (v1, v2) -> new Value(v1.value + v2.value));

        System.out.println(future.get());
    }


    @Data
    @AllArgsConstructor
    static class Value {
        private int value;
    }

}
