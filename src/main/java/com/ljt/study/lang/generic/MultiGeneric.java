package com.ljt.study.lang.generic;

/**
 * @author LiJingTang
 * @date 2021-12-09 22:09
 */
public class MultiGeneric {

    private static class AAndBGeneric extends AAndB<AB> {}
    private static class AAndBGeneric1 extends AOrB<A1, B1> {}

}



abstract class AAndB<T extends A & B> {

}

abstract class AOrB<T extends A, B> {

}

abstract class AOrC<T extends A, C> {

}

interface A {}
interface B {}
abstract class C {}
class AB implements A,B {}
class A1 implements A {}
class B1 implements B {}
