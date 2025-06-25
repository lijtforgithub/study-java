package com.ljt.study.lang.jdk.jdk10;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:28
 */
public class OptionalTest {

    @Test
    void orElseThrow() {
        Optional.ofNullable(null)
                .orElseThrow(() -> new RuntimeException("Optional.orElseThrow"));
    }

}
