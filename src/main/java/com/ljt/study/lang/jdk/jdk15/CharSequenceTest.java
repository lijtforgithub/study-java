package com.ljt.study.lang.jdk.jdk15;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:14
 */
public class CharSequenceTest {

    @Test
    void isEmpty() {
        CharSequence charSequence = "abc";
        System.out.println(charSequence.isEmpty());
        System.out.println("".isEmpty());
    }

}
