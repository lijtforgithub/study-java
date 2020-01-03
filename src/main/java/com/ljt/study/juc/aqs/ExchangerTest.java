package com.ljt.study.juc.aqs;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程交换数据
 *
 * @author LiJingTang
 * @date 2020-01-03 10:43
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<String>();

        service.execute(() -> {
            try {
                String originData = "一千万美金";
                System.out.println("线程" + Thread.currentThread().getName() + "要交换的数据: " + originData);
                Thread.sleep((long) (Math.random() * 10000));
                String data = exchanger.exchange(originData);
                System.out.println("线程" + Thread.currentThread().getName() + "交换回的数据: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                String originData = "一栋豪华别墅外送十个美女";
                System.out.println("线程" + Thread.currentThread().getName() + "要交换的数据: " + originData);
                Thread.sleep((long) (Math.random() * 10000));
                String data = exchanger.exchange(originData);
                System.out.println("线程" + Thread.currentThread().getName() + "交换回的数据: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
    }

}
