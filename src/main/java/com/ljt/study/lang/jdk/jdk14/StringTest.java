package com.ljt.study.lang.jdk.jdk14;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:12
 */
public class StringTest {

    @Test
    void test() {
        String str = """
        凡心所向，素履所往， \
        生如逆旅，一苇以航。""";
        System.out.println(str);

        String text = """
        java
        c++\sphp
        """;
        System.out.println(text);
    }

}
