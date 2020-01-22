package com.ljt.study.jvm.dataarea;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *  VM args：-Xmx6M -XX:-UseGCOverheadLimit
 *
 * @author LiJingTang
 * @date 2020-01-22 10:20
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int i = 0;

        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }

    @Test
    public void testIntern() {
        String str = new String("Hello Intern");
        System.out.println(str.intern() == str);

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

}
