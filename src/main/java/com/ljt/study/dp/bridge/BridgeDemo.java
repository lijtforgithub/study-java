package com.ljt.study.dp.bridge;

/**
 * @author LiJingTang
 * @date 2019-12-28 16:23
 */
public class BridgeDemo {

    public static void main(String[] args) {
        IPost p = new SimplePost();
        Letter letter = new Letter(p);
        letter.post();
    }

    /**
     * 平邮
     */
    private static class SimplePost implements IPost {

        @Override
        public void post() {
            System.out.println("This is Simple post");
        }
    }

    /**
     * 挂号
     */
    private static class MarkPost implements IPost {

        @Override
        public void post() {
            System.out.println("This is Mark post");
        }
    }


    private interface IPost {

        void post();

    }

    private static abstract class AbstractThing {

        private IPost obj;

        public AbstractThing(IPost obj) {
            this.obj = obj;
        }

        public void post() {
            obj.post();
        }
    }

    /**
     * 信件
     */
    private static class Letter extends AbstractThing {

        public Letter(IPost obj) {
            super(obj);
        }
    }

    /**
     * 包裹
     */
    private static class Parcel extends AbstractThing {

        public Parcel(IPost obj) {
            super(obj);
        }
    }

}
