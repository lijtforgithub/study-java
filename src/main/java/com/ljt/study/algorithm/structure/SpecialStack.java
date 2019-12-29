package com.ljt.study.algorithm.structure;

import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

/**
 * @author LiJingTang
 * @date 2019-12-29 21:20
 */
public class SpecialStack<E extends Comparable<E>> {

    public static void main(String[] args) {
        SpecialStack<Integer> stack = new SpecialStack<>();
        stack.push(300);
        stack.push(400);
        System.out.println(stack.getMin());
        stack.push(100);
        System.out.println(stack.getMin());
        stack.push(100);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
    }

    private Stack<E> dataStack = new Stack<>();
    private Stack<E> minStack = new Stack<>();

    public synchronized E push(E e) {
        Objects.requireNonNull(e);

        if (minStack.isEmpty() || minStack.peek().compareTo(e) > 0) {
            minStack.push(e);
        }

        return dataStack.push(e);
    }

    public E pop() {
        if (dataStack.isEmpty()) {
            throw new EmptyStackException();
        }

        E e = dataStack.pop();
        // 此处如果要做完美 要考虑常量池的问题
        if (e == getMin()) {
            minStack.pop();
        }

        return e;
    }

    public E getMin() {
        if (minStack.isEmpty()) {
            throw new EmptyStackException();
        }

        return minStack.peek();
    }

}
