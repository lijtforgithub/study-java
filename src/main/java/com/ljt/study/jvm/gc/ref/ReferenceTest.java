package com.ljt.study.jvm.gc.ref;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

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

}
