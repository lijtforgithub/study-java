package com.ljt.study.lang.type.clazz;

/**
 * 类构造器和实例构造器
 * <p>
 * 对象实例化过程
 * <p>
 * 1.申请对象内存
 * 2.成员变量赋默认值
 * 3.调用构造方法<init>
 * 1）成员变量顺序赋初始值
 * 2）执行构造方法语句
 *
 * @author LiJingTang
 * @date 2019-11-22 21:20
 */
public class CinitAndInit {

    public static void main(String[] args) {
        {
            System.out.println("方法内普通代码块");
        }
        System.out.println(new Child());

        synchronized ("") {
            System.out.println("方法内线程同步块");
        }
    }

    private static class Parent {

        private static Attr staticAttr1 = new Attr("父类第一个静态变量");
        private static Attr staticAttr2 = new Attr("父类第二个静态变量");
        private Attr attr1 = new Attr("父类第一个成员变量");

        static {
            System.out.println("父类静态块语句");
        }

        Parent() {
            super();
            System.out.println("父类构造方法语句");
        }

    }

    private static class Child extends Parent {

        private static Attr staticAttr1 = new Attr("子类第一个静态变量");
        private Attr attr1 = new Attr("子类第一个成员变量");

        static {
            System.out.println("子类静态块语句");
        }

        Child() {
            super();
            System.out.println("子类构造方法语句");
        }

        {
            System.out.println("构造代码块  -> 在其他构造方法之前执行");
        }
    }

    private static class Attr {
        Attr(String msg) {
            System.out.println(msg);
        }
    }

}
