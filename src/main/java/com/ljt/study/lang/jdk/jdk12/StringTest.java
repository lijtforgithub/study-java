package com.ljt.study.lang.jdk.jdk12;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:24
 */
public class StringTest {

    @Test
    void indent() {
        String text = "Java";
        text = text.indent(4);
        System.out.println(text);
        text = text.indent(-10);
        System.out.println(text);
    }

    @Test
    void transform() {
        String result = "foo".transform(input -> input + " bar");
        System.out.println(result); // foo bar
    }

}
