package com.ljt.study.pp.effective.chap04;

import java.util.*;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:04
 */
public class Item16Forward {

    public static void main(String[] args) {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>(new TreeSet<String>());
        set.addAll(Arrays.asList("Snap", "Crackle", "Pop"));
        System.out.println(set.getAddCount());
        System.out.println(set.size());
    }

    private static class InstrumentedHashSet<E> extends ForwardingSet<E> {

        private int addCount = 0;

        public InstrumentedHashSet(Set<E> set) {
            super(set);
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

    private static class ForwardingSet<E> implements Set<E> {

        private final Set<E> set;

        public ForwardingSet(Set<E> set) {
            super();
            this.set = set;
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean isEmpty() {
            return set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return set.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return set.iterator();
        }

        @Override
        public Object[] toArray() {
            return set.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return set.toArray(a);
        }

        @Override
        public boolean add(E e) {
            return set.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return set.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return set.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return set.addAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return set.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return set.removeAll(c);
        }

        @Override
        public void clear() {
            set.clear();
        }
    }

}
