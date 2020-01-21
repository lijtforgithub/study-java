package com.ljt.study.ee.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author LiJingTang
 * @date 2020-01-21 16:35
 */
public class SPITest {

    public static void main(String[] args) {
        ServiceLoader<SPICarService> loader = ServiceLoader.load(SPICarService.class);
        Iterator<SPICarService> iterator = loader.iterator();

        while (iterator.hasNext()) {
            iterator.next().print();
        }
    }

}
