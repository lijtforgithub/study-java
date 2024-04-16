package com.ljt.study.jvm.jmm;

import com.ljt.study.juc.ThreadUtils;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author LiJingTang
 * @date 2021-04-19 15:51
 */
public class ClassLayoutTest {

    public static void main(String[] args) {
        // JVM偏向锁延迟
        ThreadUtils.sleepSeconds(5);

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());


        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

}
