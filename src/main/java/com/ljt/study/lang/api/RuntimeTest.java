package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2021-07-14 10:03
 */
class RuntimeTest {

    @Test
    void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("addShutdownHook")));
    }

}
