package com.ljt.study.lang.jdk.jdk14;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:08
 */
public class NullPointerExceptionTest {

    @Test
    void test() {
        Object obj = null;
        System.out.println(obj.hashCode());
    }

}
