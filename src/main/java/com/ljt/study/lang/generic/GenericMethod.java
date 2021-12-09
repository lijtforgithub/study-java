package com.ljt.study.lang.generic;

/**
 * 泛型方法
 *
 * @author LiJingTang
 * @date 2019-11-23 14:35
 */
public class GenericMethod {

    public static void main(String[] args) {
        Integer[] integers = {1, 2, 3, 4, 5};
        String[] strings = {"London", "Paris", "New York", "Austin"};

        GenericMethod.<Integer>print(integers);
        GenericMethod.print(strings);
    }

    private static <E> void print(E[] list) {
        for (E e : list) {
            System.out.print(e + " ");
        }

        System.out.println();
    }

}
