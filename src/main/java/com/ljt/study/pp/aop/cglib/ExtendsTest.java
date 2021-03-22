package com.ljt.study.pp.aop.cglib;

/**
 * @author LiJingTang
 * @date 2021-03-22 10:43
 */
public class ExtendsTest {

    public static void main(String[] args) {
        ChildClass childClass = new ChildClass();
        childClass.print();
    }

}

class ParentClass {

    public void print() {
        System.out.println(this.getClass() + ".print()");
    }

}

class ChildClass extends ParentClass {

    @Override
    public void print() {
        System.out.println("before");
        System.out.println(super.equals(this));
        super.print();
        System.out.println("after");
    }

}
