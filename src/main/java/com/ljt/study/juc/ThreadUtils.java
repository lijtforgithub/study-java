package com.ljt.study.juc;

import java.util.concurrent.TimeUnit;

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

}
