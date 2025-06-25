package com.ljt.study.lang.jdk.jdk9;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author LiJingTang
 * @date 2025-06-25 10:54
 */
public class OptionalTest {

    @Test
    void ifPresentOrElse() {
        Optional.ofNullable("A").ifPresentOrElse(System.out::println, () -> System.out.println("null"));
        Optional.ofNullable(null).ifPresentOrElse(System.out::println, () -> System.out.println("null"));
    }

    @Test
    void or() {
        Optional.ofNullable("A").or(() -> Optional.of("B")).ifPresent(System.out::println);
        Optional.ofNullable(null).or(() -> Optional.of("B")).ifPresent(System.out::println);
    }

    @Test
    void stream() {
        Optional.ofNullable("a").stream().map(String::toUpperCase).forEach(System.out::println);
    }

}
