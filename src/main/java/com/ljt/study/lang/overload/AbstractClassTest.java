package com.ljt.study.lang.overload;

/**
 * @author LiJingTang
 * @date 2019-11-22 21:05
 */
public class AbstractClassTest {

    public static void main(String[] args) {
        String info = "相同包下的子类 ";
        SuperClass obj = null;
//		obj = new AbstractClass(); // 不能new抽象类 编译同不过
        obj = new ChildClass(info);
        obj.abstractMethod();
        obj.protectedMethod(info + "的main方法");
        obj.publicMethod(info + "的main方法");
    }

    private static class ChildClass extends SuperClass {

        private String info;

        public ChildClass(String info) {
            super();
            this.info = info;
            System.out.println("子类无参构造方法 -> 同时调用抽象父类构造方法");
        }

        @Override
        public void abstractMethod() {
            System.out.println(this.info + "：" + super.default_s + super.protected_i + SuperClass.public_l);
            super.protectedMethod(this.info);
            super.publicMethod(this.info);
        }
    }

    public static abstract class SuperClass {
        private byte private_b;
        short default_s;
        protected int protected_i;
        public static long public_l = 3;

        // 至少有一个实现类可访问的构造方法，否则实现类编译通不过
        public SuperClass() {
            this.private_b = 0;
            this.default_s = 1;
            this.protected_i = 2;
            System.out.println("抽象类可以有构造方法 -> 不能new，可以被实现类调用");
        }

        public abstract void abstractMethod(); // 抽象方法(含有抽象方法的类一定是抽象类)

        private void privateMethod() {
            System.out.println("私有非抽象方法");
        }

        protected void protectedMethod(String invokeInfo) {
            System.out.println(invokeInfo + "父类的protected方法");
        }

        public void publicMethod(String invokeInfo) {
            System.out.println(invokeInfo + "父类的public方法");
        }
    }

}
