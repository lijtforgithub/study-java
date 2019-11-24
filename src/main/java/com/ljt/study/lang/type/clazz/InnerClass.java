package com.ljt.study.lang.type.clazz;

/**
 * 内部类
 *
 * @author LiJingTang
 * @date 2019-11-22 21:57
 */
public class InnerClass {

    public static void main(String[] args) {
        Outer outer = new Outer();
        // 外部类和访问内部类的私有方法
        outer.outerMethod();

        Outer.Inner inner = outer.new Inner();
        inner.outerMethod();
        Outer.Nested nested = new Outer.Nested();
        nested.outerMethod();
    }

}

class Outer {

    private int i = 0;
    private static float f = 0.01F;

    public void outerMethod() {
        getInner().privateMethod();
    }

    public Inner getInner() {
        return new Inner();
    }

    class Inner {
        // private static int i; // 非静态内部类不能含有static的变量
        private static final double d = 0.01; // 可以有静态常量
        private int i = 10; // 可以和外部类字段名相同
        private int j = 11;

        // 可以和外部类方法名相同
        void outerMethod() {
            System.out.println(Outer.this.i);
            System.out.println(Outer.f);
            Outer.this.i++;
            i++;
            j++;
        }

        private void privateMethod() {
            System.out.println("内部类私有方法");
        }

        // 非静态内部类不能含有static的方法
        // private static void innerMethod(){
        // }
    }

    static class Nested {
        private static int i = 12;

        void outerMethod() {
            // System.out.println(Outer.this.i); // 静态内部类不能访问外部非静态成员
            System.out.println(Outer.f);
        }

        private void nestedMethod() {
            System.out.println(i);
        }
    }
}

class OuterDemo {

    class Inner extends HasStatic {
        static final int x = 3; // ok - compile-time constact
        // static int y = 4; // compile-time error,an inner class
    }

    static class NestedButNotInner {
        static int z = 5; // ok,not an inner class
    }

    interface NeverInner {
    } // interface are never inner 隐式静态的
}

class HasStatic {
    static int j = 100;
}
