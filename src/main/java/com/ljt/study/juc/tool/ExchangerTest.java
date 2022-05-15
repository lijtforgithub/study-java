package com.ljt.study.juc.tool;

import com.ljt.study.juc.ThreadUtils;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程交换数据
 *
 * @author LiJingTang
 * @date 2020-01-03 10:43
 */
class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<>();

        executor.execute(() -> {
            try {
                String originData = "一千万美金";
                System.out.println(Thread.currentThread().getName() + " 要交换的数据: " + originData);
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                String data = exchanger.exchange(originData);
                System.out.println(Thread.currentThread().getName() + " 交换回的数据: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                String originData = "一栋豪华别墅外送十个美女";
                System.out.println(Thread.currentThread().getName() + " 要交换的数据: " + originData);
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                String data = exchanger.exchange(originData);
                System.out.println(Thread.currentThread().getName() + " 交换回的数据: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }

}
