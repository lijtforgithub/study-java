package com.ljt.study.juc.pool;

import com.ljt.study.juc.ThreadUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    @Test
    void join() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(1);
            return new Value(1);
        });
        // join 也阻塞 但是没有异常
        System.out.println(future.join());
    }

    @SneakyThrows
    @Test
    void complete() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(5);

            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(5);
            return new Value(2);
        });

        new Thread(() -> {
            ThreadUtils.sleepSeconds(3);
            System.out.println(Thread.currentThread().getName() + " : " + future2.complete(new Value(20)));
        }).start();

        System.out.println(future1.complete(new Value(0)) + " - " +  future1.get());
        System.out.println(future2.get());
    }

    @SneakyThrows
    @Test
    void completedFuture() {
        CompletableFuture<Value> future = CompletableFuture.completedFuture(new Value(1));
        System.out.println(future.get());
    }

    @Test
    void whenComplete() {
        CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(5);
            int i = 1 / 0;
            return new Value(1);
        }).whenComplete((v, t) -> {
            System.out.println(v);
            System.out.println("whenComplete: " + t);
        }).exceptionally(t -> {
            System.out.println("exceptionally: " + t);
            return new Value(0);
        });

        ThreadUtils.sleepSeconds(6);
    }

    @SneakyThrows
    @Test
    void allOf() {
        CompletableFuture<Value> future1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
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
            return new Value(1);
        });

        CompletableFuture<Value> future2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            return new Value(2);
        });

        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2);
        System.out.println("end：" + future.get());
    }

    @SneakyThrows
    @Test
    void applyToEither() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            return new Value(1);
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(RANDOM.nextInt(10));
            return new Value(2);
        }), v -> {
            System.out.println(v);
            return new Value(0);
        });

        System.out.println("end：" + future.get());
    }

    @SneakyThrows
    @Test
    void thenApply() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            return new Value(1);
        }).thenApply(v -> new Value(v.v * 10));

        System.out.println(future.get());
    }

    @SneakyThrows
    @Test
    void thenAccept() {
        CompletableFuture.supplyAsync(() -> new Value(1))
                .thenApply(v -> new Value(v.v * 10))
                .thenAccept(System.out::println);
    }

    @SneakyThrows
    @Test
    void handle() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(1);
            return new Value(1 / 0);
        }).handle((v, t) -> {
            System.out.println(2);
            return new Value(Objects.nonNull(t) ? 1 : v.v + 1);
        }).thenApply(v -> {
            System.out.println(3);
            return new Value(v.v * 10);
        }).thenAccept(System.out::println);
    }

    @SneakyThrows
    @Test
    void thenCombine() {
        CompletableFuture<Value> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(2);
            System.out.println(Thread.currentThread().getName() + " : " + 1);
            return new Value(1);
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            ThreadUtils.sleepSeconds(1);
            System.out.println(Thread.currentThread().getName() + " : " + 2);
            return new Value(2);
        }), (v1, v2) -> {
            System.out.println(v1.v + v2.v);
            return new Value(0);
        });

        System.out.println(future.get());
    }

    @SneakyThrows
    @Test
    void thenCompose() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> new Value(1))
                .thenCompose(v -> CompletableFuture.completedFuture(v.v));

        System.out.println(future.get());
    }


    @Data
    @AllArgsConstructor
    static class Value {
        private int v;
    }

}
