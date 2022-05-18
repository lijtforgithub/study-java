package com.ljt.study.lang.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author LiJingTang
 * @date 2022-04-17 20:42
 */
class OptionalTest {

    @Test
    void test() {
        Object obj = null;

        Object o = Optional.ofNullable(obj).orElseGet(Object::new);
        System.out.println(o);

        Optional.ofNullable(obj).ifPresent(System.out::println);
    }

}
