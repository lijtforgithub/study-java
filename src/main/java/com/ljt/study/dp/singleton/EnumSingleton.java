package com.ljt.study.dp.singleton;

/**
 * @author LiJingTang
 * @date 2025-08-01 15:53
 */
public class EnumSingleton {

    private EnumSingleton() {
    }
    public static EnumSingleton getInstance() {
        return Holder.HOLDER.instance;
    }

    private enum Holder {
        HOLDER;
        private final EnumSingleton instance;
        Holder() {
            instance = new EnumSingleton();
        }
    }

}
