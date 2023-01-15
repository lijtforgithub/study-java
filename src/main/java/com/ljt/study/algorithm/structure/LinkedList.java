package com.ljt.study.algorithm.structure;

import lombok.Data;

import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-01-07 21:04
 */
public final class LinkedList {

    private LinkedList() {}

    /**
     * 单链表
     */
    @Data
    public static class SingleNode<E> {

        private E item;
        private SingleNode<E> next;

    }


    /**
     * 双链表
     */
    @Data
    public static class DoubleNode<E> {

        private E item;
        private DoubleNode<E> pre;
        private DoubleNode<E> next;

    }


    public static <E> void printLinkedList(SingleNode<E> node) {
        StringBuilder sbuilder = new StringBuilder();

        if (Objects.nonNull(node)) {
            sbuilder.append(node.getItem().toString());
            SingleNode<E> cur = node;

            while (Objects.nonNull(cur.getNext())) {
                sbuilder.append(" -> ").append(cur.getNext().getItem().toString());
                cur = cur.getNext();
            }
        }

        System.out.println(sbuilder);
    }

    public static <E> void printLinkedList(DoubleNode<E> node) {
        StringBuilder sbuilder = new StringBuilder();

        if (Objects.nonNull(node)) {
            sbuilder.append(node.getItem().toString());
            DoubleNode<E> cur = node;

            while (Objects.nonNull(cur.getNext())) {
                sbuilder.append(" -> ").append(cur.getNext().getItem().toString());
                cur = cur.getNext();
            }
        }

        System.out.println(sbuilder);
    }

}
