package com.ljt.study.lang.generic;

/**
 * @author LiJingTang
 * @date 2019-11-23 14:32
 */
public class GenericInterface {

    /**
     * 泛型接口实现类
     */
    static class ChildClass implements SuperInterface<ChildClass, ChildClass> {

        @Override
        public ChildClass getEObject() {
            return null;
        }

        @Override
        public ChildClass getTObject() {
            return null;
        }
    }

    /**
     * 泛型接口
     */
    private interface SuperInterface<E, T> {

        E getEObject();

        T getTObject();

    }

}
