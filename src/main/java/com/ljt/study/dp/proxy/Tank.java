package com.ljt.study.dp.proxy;

import com.ljt.study.juc.ThreadUtils;

import java.util.Random;

/**
 * @author LiJingTang
 * @date 2019-12-14 14:40
 */
public class Tank implements Movable {

    @Override
    public void move() {
        System.out.println("Tank Moving...");
        ThreadUtils.sleepSeconds(new Random().nextInt(5));
    }

}
