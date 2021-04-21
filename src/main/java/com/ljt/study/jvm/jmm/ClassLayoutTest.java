package com.ljt.study.jvm.jmm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author LiJingTang
 * @date 2021-04-19 15:51
 */
public class ClassLayoutTest {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

}
