package com.ljt.study.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-12-31 13:53
 */
public class JavaParamTest {

    public static void main(String[] args) {
        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean bean : beans) {
            System.out.println(bean.getName());
        }

        System.gc();

        for (; ; ) ;
    }

}
