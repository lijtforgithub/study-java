package com.ljt.study.lang.overload.inner;
import com.ljt.study.lang.overload.AbstractClassTest.Parent;

/**
 * @author LiJingTang
 * @date 2019-11-22 21:11
 */
public class AbstractClassTest {

    public static void main(String[] args) {
        String info = "不同包下的子类 ";
        Parent obj = new Child(info);
        obj.abstractMethod();
//		obj.protectedMethod(info + "的main方法"); // 不能调用 main方法不属于某个类，就像不能调用Child的私有变量info一样
        obj.publicMethod(info + "的main方法");
    }

    private static class Child extends Parent {

        private String info;

        public Child(String info) {
            super();
            this.info = info;
            System.out.println("子类类无参构造方法 -> 同时调用抽象父类构造方法");
        }

        @Override
        public void abstractMethod() {
            System.out.println("子类：" + super.protected_i + Parent.public_l);
            super.protectedMethod(this.info);
            super.publicMethod(this.info);
        }
    }

}
