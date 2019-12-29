package com.ljt.study.algorithm.structure;

/**
 * @author LiJingTang
 * @date 2019-12-29 21:20
 */
public interface Tree<E extends Comparable<E>> {

    boolean add(E e);

    boolean delete(E e);

    E find(E e);

    E findMin();

    E findMax();

    /**
     * 中序遍历:左子树——》根节点——》右子树
     */
    String midToString();

    /**
     * 前序遍历:根节点——》左子树——》右子树
     */
    String preToString();

    /**
     * 后序遍历:左子树——》右子树——》根节点
     */
    String postToString();

}
