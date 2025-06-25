package com.ljt.study.lang.jdk9;

import jdk.internal.vm.annotation.Stable;

/**
 * @author LiJingTang
 * @date 2025-06-25 10:26
 */
public class StringTest {

    /**
     * 减少了内存使用
     */
    @Stable
    private final byte[] value;

    public StringTest(byte[] value) {
        this.value = value;
    }

}
