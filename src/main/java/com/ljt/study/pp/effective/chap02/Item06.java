package com.ljt.study.pp.effective.chap02;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:55
 */
public class Item06 {

    private static class Stack {

        private Object[] elements;
        private int size;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (0 == size) {
                throw new EmptyStackException();
            }

            Object result = elements[--size];
            elements[size] = null; // 清除过期引用

            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }

}
