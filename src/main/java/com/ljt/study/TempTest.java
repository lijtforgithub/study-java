package com.ljt.study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        condition.await();
    }

}
