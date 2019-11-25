package com.ljt.study.lang.generic;

import java.util.ArrayList;

/**
 * 泛型类 模拟栈
 *
 * @author LiJingTang
 * @date 2019-11-23 14:27
 */
public class GenericClass<E> {

    private ArrayList<E> list = new ArrayList<E>();

    // 默认构造函数
    public GenericClass() {
    }


    /**
     * 返回这个栈的元素个数
     */
    public int getSize() {
        return list.size();
    }

    /**
     * 返回这个栈的栈顶元素
     */
    public E peek() {
        return list.get(getSize() - 1);
    }

    /**
     * 向这个栈的顶端添加一个元素
     */
    public void push(E o) {
        list.add(o);
    }

    /**
     * 返回并删除这个栈的栈顶元素
     */
    public E pop() {
        E o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }

    /**
     * 如果栈为空则返回true
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

}
