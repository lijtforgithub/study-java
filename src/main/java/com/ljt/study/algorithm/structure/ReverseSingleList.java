package com.ljt.study.algorithm.structure;

import java.util.Objects;
import java.util.Stack;

/**
 * 单链表反转
 *
 * @author LiJingTang
 * @date 2019-12-29 21:20
 */
public class ReverseSingleList {

    public static void main(String[] args) {
        Node<Integer> node = createNodeChin();
        printNodeChin(node);
        node = reverse3(node);
        printNodeChin(node);
    }

    /**
     * 递归实现 当栈深度大于12000 则会出现StakOverflowError
     */
    public static <E> Node<E> reverse1(Node<E> node) {
        if (Objects.isNull(node) || Objects.isNull(node.getNext())) {
            return node;
        }

        Node<E> newHead = reverse1(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return newHead;
    }

    /**
     * 遍历实现 性能最高
     */
    public static <E> Node<E> reverse2(Node<E> node) {
        if (Objects.isNull(node) || Objects.isNull(node.getNext())) {
            return node;
        }

        Node<E> pre = node;
        Node<E> cur = node.getNext();

        while (Objects.nonNull(cur.getNext())) {
            Node<E> tmp = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = tmp;
        }

        cur.setNext(pre);
        node.setNext(null);

        return cur;
    }

    /**
     * stack具有先进后出这一特性，因此可以借助于stack数据结构来实现单向链表的反转
     * 缺点在于需要通过其他数据结构实现，效率会降低
     */
    public static <E> Node<E> reverse3(Node<E> node) {
        if (Objects.isNull(node) || Objects.isNull(node.getNext())) {
            return node;
        }

        Stack<Node<E>> stack = new Stack<>();
        for (Node<E> n = node; Objects.nonNull(n); n = n.getNext()) {
            stack.add(n);
        }

        Node<E> newHead = stack.pop();
        Node<E> cur = newHead;

        while (!stack.isEmpty()) {
            cur.setNext(stack.pop());
            cur = cur.getNext();
            cur.setNext(null);
        }

        return newHead;
    }

    /**
     * 初始化值
     */
    private static Node<Integer> createNodeChin() {
        Node<Integer> head = new Node<>(0, null);
        Node<Integer> cur = head;

        for (int i = 1; i < 10; i++) {
            Node<Integer> tmp = new Node<>(i, null);
            cur.setNext(tmp);
            cur = tmp;
        }

        return head;
    }

    /**
     * 打印
     */
    private static <E> void printNodeChin(Node<E> head) {
        StringBuilder sbuilder = new StringBuilder();

        if (Objects.nonNull(head)) {
            sbuilder.append(head.getItem().toString());
            Node<E> cur = head;

            while (Objects.nonNull(cur.getNext())) {
                sbuilder.append(" -> ").append(cur.getNext().getItem().toString());
                cur = cur.next;
            }
        }

        System.out.println(sbuilder.toString());
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }

        E getItem() {
            return item;
        }

        Node<E> getNext() {
            return next;
        }

        void setNext(Node<E> next) {
            this.next = next;
        }
    }

}
