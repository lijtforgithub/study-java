package com.ljt.study.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2020-03-04 20:57
 */
class FIFO {

    public static void main(String[] args) {
        int cacheSize = 5;
        FIFOCache<Integer, Character> cache = new FIFOCache<>(cacheSize);

        IntStream.rangeClosed(1, cacheSize + 2).forEach(i -> {
            cache.put(i, (char) (64 + i));
            if (i == cacheSize) {
                System.out.println(cache.get(i));
            }
            System.out.println(cache);
        });
    }

    private static class FIFOCache<K, V> extends LinkedHashMap<K, V> {

        private static final long serialVersionUID = 6774279972321154835L;

        private final int cacheSize;

        private FIFOCache(int cacheSize) {
            this.cacheSize = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > cacheSize;
        }
    }

}
