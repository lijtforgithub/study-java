package com.ljt.study.pp.effective.chap04;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author LiJingTang
 * @date 2019-12-29 10:58
 */
public class Item16 {

    public static void main(String[] args) {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
        set.addAll(Arrays.asList("Snap", "Crackle", "Pop"));
        System.out.println(set.getAddCount());
        System.out.println(set.size());
    }

    private static class InstrumentedHashSet<E> extends HashSet<E> {

        private static final long serialVersionUID = -3345545446931309242L;

        private int addCount = 0;

        public InstrumentedHashSet() {
        }

        public InstrumentedHashSet(int initCap, float loadFactor) {
            super(initCap, loadFactor);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

}
