package com.ljt.study.lang.overload.inner;
import com.ljt.study.lang.overload.AbstractClassTest.SuperClass;

/**
 * @author LiJingTang
 * @date 2019-11-22 21:11
 */
public class AbstractClassTest {

    public static void main(String[] args) {
        String info = "不同包下的子类 ";
        SuperClass obj = new ChildClass(info);
        obj.abstractMethod();
//		obj.protectedMethod(info + "的main方法"); // 不能调用 main方法不属于某个类，就像不能调用ChildClass的私有变量info一样
        obj.publicMethod(info + "的main方法");
    }

    private static class ChildClass extends SuperClass {

        private String info;

        public ChildClass(String info) {
            super();
            this.info = info;
            System.out.println("子类类无参构造方法 -> 同时调用抽象父类构造方法");
        }

        @Override
        public void abstractMethod() {
            System.out.println("子类：" + super.protected_i + SuperClass.public_l);
            super.protectedMethod(this.info);
            super.publicMethod(this.info);
        }
    }

}
