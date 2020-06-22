package com.ljt.study.jvm.instruction;

/**
 * 方法调用-单/多分派
 *
 * @author LiJingTang
 * @date 2019-12-05 14:36
 */
public class MIDispatch {

    public static void main(String[] args) {
        Parent p = new Parent();
        Parent c = new Child();
        p.hardChoice(new _360());
        c.hardChoice(new QQ());
    }

    private static class Parent {
        public void hardChoice(QQ args) {
            System.out.println("parent choice qq");
        }

        public void hardChoice(_360 args) {
            System.out.println("parent choice 360");
        }
    }

    private static class Child extends Parent {
        @Override
        public void hardChoice(QQ args) {
            System.out.println("child choice qq");
        }

        @Override
        public void hardChoice(_360 args) {
            System.out.println("child choice 360");
        }
    }

    private static class QQ {
    }

    private static class _360 {
    }

}
