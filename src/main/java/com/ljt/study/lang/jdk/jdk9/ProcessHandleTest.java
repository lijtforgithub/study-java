package com.ljt.study.lang.jdk.jdk9;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 10:57
 */
public class ProcessHandleTest {

    @Test
    void processHandle() {
        // 获取当前正在运行的 JVM 的进程
        ProcessHandle currentProcess = ProcessHandle.current();
        // 输出进程的 id
        System.out.println(currentProcess.pid());
        // 输出进程的信息
        System.out.println(currentProcess.info());
    }

}
