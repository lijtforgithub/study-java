package com.ljt.study.lang.jdk.jdk11;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:49
 */
public class OptionalTest {

    @Test
    void isEmpty() {
        var op = Optional.empty();
        // 判断指定的 Optional 对象是否为空
        System.out.println(op.isEmpty());
    }
}
