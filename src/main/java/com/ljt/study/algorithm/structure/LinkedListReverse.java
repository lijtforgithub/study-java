package com.ljt.study.algorithm.structure;

import java.util.Objects;
import java.util.Stack;

/**
 * @author LiJingTang
 * @date 2023-01-07 21:11
 */
public class LinkedListReverse {

    /**
     * 遍历实现 性能最高
     */
    public static <E> LinkedList.SingleNode<E> reverseSingleNode(LinkedList.SingleNode<E> node) {
        LinkedList.SingleNode<E> pre = null;
        LinkedList.SingleNode<E> next;

        while (Objects.nonNull(node)) {
            next = node.getNext();
            node.setNext(pre);

            pre = node;
            node = next;
        }

        return pre;
    }

    /**
     * 递归实现 当栈深度大于12000 则会出现StackOverflowError
     */
    public static <E> LinkedList.SingleNode<E> reverseSingleNodeForRecursion(LinkedList.SingleNode<E> node) {
        if (Objects.isNull(node) || Objects.isNull(node.getNext())) {
            return node;
        }

        LinkedList.SingleNode<E> newHead = reverseSingleNodeForRecursion(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return newHead;
    }

    /**
     * stack具有先进后出这一特性，因此可以借助于stack数据结构来实现单向链表的反转
     * 缺点在于需要通过其他数据结构实现，效率会降低
     */
    public static <E> LinkedList.SingleNode<E> reverseSingleNodeForStack(LinkedList.SingleNode<E> node) {
        if (Objects.isNull(node) || Objects.isNull(node.getNext())) {
            return node;
        }

        Stack<LinkedList.SingleNode<E>> stack = new Stack<>();
        for (LinkedList.SingleNode<E> n = node; Objects.nonNull(n); n = n.getNext()) {
            stack.add(n);
        }

        LinkedList.SingleNode<E> newHead = stack.pop();
        LinkedList.SingleNode<E> cur = newHead;

        while (!stack.isEmpty()) {
            cur.setNext(stack.pop());
            cur = cur.getNext();
            cur.setNext(null);
        }

        return newHead;
    }


    public static <E> LinkedList.DoubleNode<E> reverseDoubleNode(LinkedList.DoubleNode<E> node) {
        LinkedList.DoubleNode<E> pre = null;
        LinkedList.DoubleNode<E> next = null;

        while (Objects.nonNull(node)) {
            next = node.getNext();
            node.setPre(next);
            node.setNext(pre);

            pre = node;
            node = next;
        }

        return pre;
    }

}
