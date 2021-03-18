package com.ljt.study.jvm.gc;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.lang.ref.*;
import java.lang.reflect.Field;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:53
 */
public class ReferenceTest {

    /**
     * 强引用：没有引用才会被回收
     */
    @Test
    public void testStrong() {
        RefObj obj = new RefObj();
        System.gc();

        ThreadUtils.sleepSeconds(5);

        obj = null;
        System.gc();

        ThreadUtils.sleepSeconds(2);
    }

    /**
     * 弱引用：发生GC就会被回收
     */
    @Test
    public void testWeak() {
        WeakReference<RefObj> wf = new WeakReference<>(new RefObj());
        System.out.println(wf.get());
        System.gc();

        ThreadUtils.sleepSeconds(2);

        System.out.println(wf.get());
    }

    /**
     * 软引用：内存不足时GC就会被回收
     * 启动时设置堆最大20MB
     */
    @Test
    public void testSoft() {
        SoftReference<byte[]> sf = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(sf.get());
        System.gc();
        ThreadUtils.sleepSeconds(2);
        System.out.println(sf.get());

        byte[] array = new byte[1024 * 1024 * 10];
        ThreadUtils.sleepSeconds(5);
        System.out.println(sf.get());
    }

    private static class RefObj {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println(this.getClass().getName() + "：finalize");
        }
    }


    private static volatile boolean isRun = true;


    /**
     * 虚引用：一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来取得一个对象实例。
     * 为一个对象设置虚引用关联的唯一目的只是为了能在这个对象被收集器回收时收到一个系统通知。
     * 常用于堆外内存的引用，引用为null后因为内存不在JVM，无法回收；引用放到队列，从队列获取到通知。Unsafe回收堆外内存。
     */
    @Test
    public void testPhantom() {
        String str = new String("XXOO");
        System.out.println(str.getClass() + "@" + str.hashCode());
        /*
         * 没有引用后放到此队列
         */
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();

        new Thread(() -> {
            while (isRun) {
                Object o = referenceQueue.poll();
                if (o != null) {
                    try {
                        Field referent = Reference.class.getDeclaredField("referent");
                        referent.setAccessible(true);
                        Object result = referent.get(o);
                        System.out.println("通知回收此对象：" + result.getClass() + "@" + result.hashCode() + "\t" + result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        PhantomReference<String> pf = new PhantomReference<>(str, referenceQueue);
        /*
         * get 不到 主要是为了有个通知
         */
        System.out.println(pf.get());
        str = null;
        ThreadUtils.sleepSeconds(3);
        System.gc();
        ThreadUtils.sleepSeconds(10);
        isRun = false;
    }

}
