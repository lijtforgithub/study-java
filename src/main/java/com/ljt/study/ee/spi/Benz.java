package com.ljt.study.ee.spi;

/**
 * @author LiJingTang
 * @date 2020-01-21 16:21
 */
public class Benz implements SPICarService {

    @Override
    public void print() {
        System.out.println("梅赛德斯-奔驰");
    }

}
