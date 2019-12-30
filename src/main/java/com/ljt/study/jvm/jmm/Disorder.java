package com.ljt.study.jvm.jmm;

import com.ljt.study.juc.ThreadUtils;

/**
 * 指令乱序证明
 *
 * @author LiJingTang
 * @date 2019-11-23 21:56
 */
public class Disorder {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread(() -> {
                //由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                shortWait();
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            t1.start();
            t2.start();
            ThreadUtils.join(t1, t2);

            String result = "第" + i + "次 (" + x + "," + y + "）";
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                //System.out.println(result);
            }
        }
    }

    private static void shortWait() {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + (long) 100000 >= end);
    }

}
