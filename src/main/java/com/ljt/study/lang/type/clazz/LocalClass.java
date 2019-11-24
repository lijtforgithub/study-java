package com.ljt.study.lang.type.clazz;

/**
 * 局部内部类（包括匿名局部内部类和普通内部类）中使用局部变量，那么这个局部变量必须使用final修饰。
 * 这里说的事局部内部类，不是普通静态内部类和非静态内部类，因为他们不能访问方法体内的局部变量。
 * java要求所有被局部内部类访问的局部变量都是用final修饰是因为：对于普通局部变量他的作用域就是该方法内，当方法结束该局部变量就随之消失；
 * 但局部内部类可能产生隐式的闭包，闭包将使得局部变量脱离他所在的方法继续存在。
 *
 * @author LiJingTang
 * @date 2019-11-22 21:43
 */
public class LocalClass {

    /**
     * 定义了一个局部变量str。正常情况下，当程序执行完①行代码之后，main方法的生命周期就结束了，局部变量str的作用域也会随之结束。
     * 但只要线程里run方法没有执行完，匿名内部类的生命周期就没有结束，将一直可以访问str局部变量的值，这个就是内部类会扩大局部变量作用域的实例。
     * 由于内部类可能扩大局部变量的作用域，如果再加上这个被内部类访问的局部变量没有使用final修饰，也就是说这个变量的值可以随时改变，那将引起极大的混乱，
     * 因此java编译器要求所有被内部类访问的局部变量必须使用final修饰符修饰。
     */
    public static void main(String[] args) {
        final String str = "Java"; // 定义一个局部变量

        // 在内部类里访问局部变量str
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(str + " " + i); // 此处将一直可以访问到str局部变量
            }
        }).start(); // ①
    }

}

class LocalDemo {

    int i = 100;

    static void classMethod() {
        final int l = 200;

        class LocalInStaticContext {
            // int k = i; // compile-time error
            int m = l; // ok
        }
    }

    void foo() {
        class Local { // a local class
            int j = i;
        }
    }
}
