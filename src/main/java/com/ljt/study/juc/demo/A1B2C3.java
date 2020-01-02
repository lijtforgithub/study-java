package com.ljt.study.juc.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author LiJingTang
 * @date 2020-01-02 19:32
 */
public class A1B2C3 {

    private static final String NUMBER = "0123456789";
    private static final String CHAR = "ABCDEFGHIJ";
    private Thread tn;
    private Thread tc;

    @Test
    public void lockSupport() throws InterruptedException {
        tn = new Thread(() -> {
            for (int i = 0; i < NUMBER.length(); i++) {
                System.out.print(NUMBER.charAt(i));
                LockSupport.unpark(tc);
                LockSupport.park();
            }
        });

        tc = new Thread(() -> {
            for (int i = 0; i < CHAR.length(); i++) {
                LockSupport.park();
                System.out.print(CHAR.charAt(i));
                LockSupport.unpark(tn);
            }
        });

        tn.start();
        tc.start();
        tn.join();
        tc.join();
    }

}
