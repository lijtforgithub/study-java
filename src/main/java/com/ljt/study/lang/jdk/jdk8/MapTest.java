package com.ljt.study.lang.jdk.jdk8;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2019-12-29 13:43
 */
public class MapTest {

    private Map<String, Integer> map = new HashMap<>();
    private static final String KEY = "k";

    @Test
    public void testPutIfAbsent() {
        IntStream.range(1, 6).forEach(i -> map.putIfAbsent(KEY, i));
        System.out.println(map);
    }

    @Test
    public void testMerge() {
        IntStream.range(1, 6).forEach(i -> map.merge(KEY, i, Integer::sum));
        System.out.println(map);
    }

    @Test
    public void testGetOrDefault() {
        IntStream.range(1, 6).forEach(i -> map.put(KEY, map.getOrDefault(KEY, 1) + i));
        System.out.println(map);
    }

    @Test
    public void testCompute() {
        IntStream.range(1, 6).forEach(i -> map.compute(KEY, (k, v) -> {
            System.out.println(k);
            return v;
        }));
        System.out.println(map);
    }

}
