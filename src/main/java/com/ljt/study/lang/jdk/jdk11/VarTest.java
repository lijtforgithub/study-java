package com.ljt.study.lang.jdk.jdk11;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:52
 */
public class VarTest {

    @Test
    void test() {
        // 下面两者是等价的
        Consumer<String> consumer1 = (var i) -> System.out.println(i);
        Consumer<String> consumer2 = (String i) -> System.out.println(i);
    }

}
