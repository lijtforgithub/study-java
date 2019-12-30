package com.ljt.study.lang.collection;

import com.ljt.study.juc.ThreadUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2019-11-27 20:52
 */
public class FailFastTest {

    public static void main(String[] args) {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        Thread t1 = new Thread(() -> list.forEach(i -> {
            System.out.println(Thread.currentThread().getName() + "遍历：" + i);
            ThreadUtils.sleepSeconds(1);
        }));

        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i < 6) {
                System.out.println(Thread.currentThread().getName() + "run：" + i);
                if (i == 3) {
                    list.remove(i);
                }
                i++;
            }
        });

        t1.start();
        t2.start();
    }


}
