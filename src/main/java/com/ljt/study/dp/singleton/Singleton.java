package com.ljt.study.dp.singleton;

import java.util.Objects;

/**
 * 单例模式
 *
 * @author LiJingTang
 * @date 2019-12-06 14:11
 */
public class Singleton {

    /**
     * 双重检锁
     */
    private static class DCLSingleton {

        /**
         * volatile 是必须的保持可见性和禁止指令重排
         */
        private static volatile DCLSingleton instance = null;

        private DCLSingleton() {
        }

        public static DCLSingleton getInstance() {
            if (Objects.isNull(instance)) {
                synchronized (DCLSingleton.class) {
                    if (Objects.isNull(instance)) {
                        instance = new DCLSingleton();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 枚举
     * 这种方式是Effective Java作者Josh Bloch提倡的方式，它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
     */
    private enum EnumSingleton {
        INSTANCE;
    }

    /**
     * 静态内部类
     *
     * 这种方式同样利用了classloder的机制来保证初始化instance时只有一个线程。
     * Singleton类被装载了，instance不一定被初始化。因为SingletonHolder类没有被主动使用，
     * 只有显式通过调用getInstance方法时，才会显示装载SingletonHolder类，从而实例化instance。
     * 如果实例化instance很消耗资源，我想让他延迟加载，另外一方面，我不希望在Singleton类加载时就实例化，
     * 因为我不能确保Singleton类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化instance显然是不合适的。
     * 这种方式相比饿汉方式就显得很合理。
     */
    private static class InnerSingleton {

        private InnerSingleton() {
        }

        public static InnerSingleton getInstance() {
            return SingleHolder.INSTANCE;
        }

        private static class SingleHolder {
            private static final InnerSingleton INSTANCE = new InnerSingleton();
        }
    }

    /**
     * 饿汉 线程安全
     *
     * 这种方式基于classloder机制避免了多线程的同步问题。instance在类装载时就实例化，虽然导致类装载的原因有很多种，在单例模式中大多数都是调用getInstance方法，
     * 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到lazy loading的效果。
     */
    private static class EagerSingleton {

        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {
        }

        public static EagerSingleton getInstance() {
            return INSTANCE;
        }
    }

    /**
     * 懒汉 线程安全
     */
    private static class SafeLazySingleton {

        private static SafeLazySingleton instance = null;

        private SafeLazySingleton() {
        }

        public static synchronized SafeLazySingleton getInstance() {
            if (Objects.isNull(instance)) {
                instance = new SafeLazySingleton();
            }
            return instance;
        }
    }

    /**
     * 懒汉 线程不安全
     */
    private static class UnsafeLazySingleton {

        private static UnsafeLazySingleton instance = null;

        private UnsafeLazySingleton() {
        }

        public static UnsafeLazySingleton getInstance() {
            if (Objects.isNull(instance)) {
                instance = new UnsafeLazySingleton();
            }
            return instance;
        }
    }

}
