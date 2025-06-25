package com.ljt.study.lang.jdk.jdk9;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:14
 */
public class VarHandleTest {

    private String f;

    @Test
    void varHandle() throws NoSuchFieldException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        VarHandle fh = lookup.findVarHandle(VarHandleTest.class, "f", String.class);
        fh.set(this, "hello");
        System.out.println(this.f);
    }
}
