package com.ljt.study.lang.generic;

/**
 * @author LiJingTang
 * @date 2021-12-09 22:09
 */
public class MultiGeneric {

    public static void main(String[] args) {
        A_B<AB> ab = new A_B<>();
    }

    private static class A_B<T extends A & B> {

    }

    interface A {}
    interface B {}
    private static class AB implements A,B {}

}
