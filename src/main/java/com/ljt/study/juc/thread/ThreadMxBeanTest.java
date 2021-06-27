package com.ljt.study.juc.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Java程序天生就是多线程
 *
 * @author LiJingTang
 * @date 2019-11-25 10:47
 */
public class ThreadMxBeanTest {

    public static void main(String[] args) {
        // 获取Java线程管理MXBean
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMxBean.dumpAllThreads(false, false);
        // 遍历线程信息 仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }
    }

}
