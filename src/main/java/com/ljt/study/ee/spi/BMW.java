package com.ljt.study.ee.spi;

/**
 * @author LiJingTang
 * @date 2020-01-21 16:20
 */
public class BMW implements SPICarService {

    @Override
    public void print() {
        System.out.println("别摸我");
    }

}
