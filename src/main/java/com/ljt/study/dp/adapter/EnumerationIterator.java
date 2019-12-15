package com.ljt.study.dp.adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author LiJingTang
 * @date 2019-12-14 17:18
 */
public class EnumerationIterator<E> implements Iterator<E> {

    private Enumeration<E> enums;

    public EnumerationIterator(Enumeration<E> enums) {
        super();
        this.enums = enums;
    }

    @Override
    public boolean hasNext() {
        return this.enums.hasMoreElements();
    }

    @Override
    public E next() {
        return this.enums.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
