package com.ljt.study.juc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-11-25 08:34
 */
public class ThreadUtils {

    private ThreadUtils() {
    }

    public static void sleepSeconds(long i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(long i, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void join(Thread... ts) {
        try {
            for (Thread t : ts) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printThreadStatus(String... threadNames) {
        Set<String> names = Stream.of(threadNames).collect(Collectors.toSet());
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMxBean.dumpAllThreads(false, false);

        System.out.println("----------- 打印线程状态开始 ----------");
        for (ThreadInfo threadInfo : threadInfos) {
            if (!names.isEmpty() && !names.contains(threadInfo.getThreadName())) {
                continue;
            }

            System.out.println(threadInfo.getThreadName() + " is " + threadInfo.getThreadState());
        }
        System.out.println("----------- 打印线程状态结束 ----------");
    }

}
