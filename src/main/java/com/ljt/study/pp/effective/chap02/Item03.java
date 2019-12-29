package com.ljt.study.pp.effective.chap02;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:29
 */
public class Item03 {

    private enum SingletonEnum {
        INSTANCE;
    }

    private static class SingletonClass {

        public transient static final SingletonClass INSTANCE = new SingletonClass();

        private SingletonClass() {
        }

        public static SingletonClass getInstance() {
            return INSTANCE;
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

}
