package com.ljt.study.algorithm.structure;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2023-01-07 21:30
 */
class LinkedListReverseTest {

    private static final LinkedList.SingleNode<Integer> SINGLE_NODE = new LinkedList.SingleNode<>();
    private static final LinkedList.DoubleNode<Integer> DOUBLE_NODE = new LinkedList.DoubleNode<>();

    static {
        SINGLE_NODE.setItem(0);
        LinkedList.SingleNode<Integer> cur = SINGLE_NODE;

        for (int i = 1; i <= 5; i++) {
            LinkedList.SingleNode<Integer> temp = new LinkedList.SingleNode<>();
            temp.setItem(i);
            cur.setNext(temp);
            cur = temp;
        }

        DOUBLE_NODE.setItem(0);
        LinkedList.DoubleNode<Integer> current = DOUBLE_NODE;

        for (int i = 1; i <= 5; i++) {
            LinkedList.DoubleNode<Integer> temp = new LinkedList.DoubleNode<>();
            temp.setItem(i);
            temp.setPre(current);
            current.setNext(temp);
            current = temp;
        }
    }


    @Test
    void testReverseSingleNode() {
        LinkedList.printLinkedList(SINGLE_NODE);
        LinkedList.printLinkedList(LinkedListReverse.reverseSingleNode(SINGLE_NODE));
    }

    @Test
    void testReverseDoubleNode() {
        LinkedList.printLinkedList(DOUBLE_NODE);
        LinkedList.printLinkedList(LinkedListReverse.reverseDoubleNode(DOUBLE_NODE));
    }

}
