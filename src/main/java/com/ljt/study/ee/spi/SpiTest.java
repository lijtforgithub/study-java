package com.ljt.study.ee.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author LiJingTang
 * @date 2020-01-21 16:35
 */
class SpiTest {

    public static void main(String[] args) {
        ServiceLoader<CarService> loader = ServiceLoader.load(CarService.class);
        Iterator<CarService> iterator = loader.iterator();

        while (iterator.hasNext()) {
            // java.util.ServiceLoader.LazyIterator.nextService
            // Class.forName(cn, false, loader);
            iterator.next().print();
        }
    }

}
