package com.ljt.study.juc.collection;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

/**
 * @author LiJingTang
 * @date 2021-07-07 15:16
 */
class SetTest {

    private CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
    // 可以指定顺序
    private ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>(Comparator.naturalOrder());

    @SneakyThrows
    @Test
    void test() {
        int count = 5;
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                String s = RandomStringUtils.randomAlphabetic(3).toLowerCase();
                copyOnWriteArraySet.add(s);
                concurrentSkipListSet.add(s);
                latch.countDown();
            });
            thread.start();
        }

        latch.await();
        System.out.println(copyOnWriteArraySet);
        System.out.println(concurrentSkipListSet);
    }

}
