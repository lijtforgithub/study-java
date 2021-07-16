package com.ljt.study;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    @SneakyThrows
    public static void main(String[] args) {
        Cache<Integer, String> cache =
                CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS)
                        .removalListener((RemovalNotification<Object, Object> notification) -> System.out.println("缓存过期失效: " + notification.getKey()))
                        .build();

        final String s = cache.get(1, () -> {
            System.out.println("初始化缓存");
            return "A";
        });
        System.out.println(cache.getIfPresent(1));

        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.getIfPresent(1));

        cache.invalidate(1);

        TimeUnit.SECONDS.sleep(30);

    }

}
