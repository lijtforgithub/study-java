package com.ljt.study.algorithm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Least Recently Used 即最近最少使用
 *
 * @author LiJingTang
 * @date 2020-03-04 19:08
 */
public class LRU {

    public static void main(String[] args) {
        int cacheSize = 5;
//        LRUCache<Integer, Character> cache = new LRUCache(cacheSize);
        LRUCache1<Integer, Character> cache = new LRUCache1(cacheSize);

        IntStream.rangeClosed(1, cacheSize * 2).forEach(i -> {
            if (i == cacheSize) {
                System.out.println(cache.get(1).toString() + "被访问" + cache);
            }
            cache.put(i, (char) (64 + i));
            System.out.println(cache);
        });
    }

    /**
     * 继承方式实现比较简单，而且实现了Map接口，在多线程环境使用时可以使用 Collections.synchronizedMap()方法实现线程安全操作
     */
    private static class LRUCache<K, V> extends LinkedHashMap<K, V> {

        private final int CACHE_SIZE;

        private LRUCache(int cacheSize) {
            // LinkedHashMap的一个构造函数，当参数accessOrder为true时，即会按照访问顺序排序，最近访问的放在最前，最早访问的放在后面
            super((int) ((float) cacheSize / 0.75F + 1.0F), 0.75f, true);
            this.CACHE_SIZE = cacheSize;
        }

        /**
         * 判断是否删除最老的元素方法，默认返回false
         * 重写这个方法，当满足一定条件时删除老数据
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > CACHE_SIZE;
        }
    }


    private static class LRUCache1<K, V> {

        private final int CACHE_SIZE;
        private Node first;
        private Node last;
        private HashMap<K, Node<K, V>> hashMap;

        public LRUCache1(int cacheSize) {
            this.CACHE_SIZE = cacheSize;
            hashMap = new HashMap<>();
        }

        public void put(K key, V value) {
            Node<K, V> node = getNode(key);

            if (Objects.isNull(node)) {
                if (hashMap.size() >= CACHE_SIZE) {
                    hashMap.remove(last.key);
                    removeLast();
                }

                node = new Node<>();
                node.key = key;
            }

            node.value = value;
            moveToFirst(node);
            hashMap.put(key, node);
        }

        public V get(K key) {
            Node<K, V> node = getNode(key);

            if (Objects.isNull(node)) {
                return null;
            }

            moveToFirst(node);
            return node.value;
        }

        public void remove(K key) {
            Node<K, V> node = getNode(key);

            if (Objects.nonNull(node)) {
                if (Objects.nonNull(node.prev)) {
                    node.prev.next = node.next;
                }
                if (Objects.nonNull(node.next)) {
                    node.next.prev = node.prev;
                }
                if (first == node) {
                    first = node.next;
                }
                if (last == node) {
                    last = node.prev;
                }

                hashMap.remove(key);
            }
        }

        @Override
        public String toString() {
            StringBuilder strBuilder = new StringBuilder("{");
            Node<K, V> node = last;

            while (Objects.nonNull(node)) {
                strBuilder.append(String.format("%s=%s ", node.key, node.value));
                node = node.prev;
            }

            return strBuilder.append("}").toString();
        }

        private void moveToFirst(Node<K, V> node) {
            if (first == node) {
                return;
            }
            if (last == node) {
                last = last.prev;
            }
            if (Objects.isNull(first) || Objects.isNull(last)) {
                first = last = node;
                return;
            }

            if (Objects.nonNull(node.prev)) {
                node.prev.next = node.next;
            }
            if (Objects.nonNull(node.next)) {
                node.next.prev = node.prev;
            }

            node.next = first;
            first.prev = node;
            node.prev = null;
            first = node;
        }

        private void removeLast() {
            if (Objects.nonNull(last)) {
                last = last.prev;

                if (Objects.isNull(last)) {
                    first = null;
                } else {
                    last.next = null;
                }
            }
        }

        private Node<K, V> getNode(K key) {
            return hashMap.get(key);
        }

        private static class Node<K, V> {
            K key;
            V value;
            Node prev;
            Node next;
        }
    }

}
