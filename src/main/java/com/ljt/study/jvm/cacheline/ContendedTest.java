//package com.ljt.study.jvm.cacheline;
//
//import com.ljt.study.juc.ThreadUtils;
//import sun.misc.Contended;
//
//import static com.ljt.study.jvm.cacheline.CacheLineTest.COUNT;
//
///**
// * -XX:-RestrictContended
// *
// * @author LiJingTang
// * @date 2021-08-30 20:40
// */
//public class ContendedTest {
//
//    @Contended
//    private volatile long x;
//    @Contended
//    private volatile long y;
//
//    public static void main(String[] args) {
//        ContendedTest test = new ContendedTest();
//
//        Thread t1 = new Thread(() -> {
//            for (long i = 0; i < COUNT; i++) {
//                test.x = i;
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < COUNT; i++) {
//                test.y = i;
//            }
//        });
//
//        final long startTime = System.currentTimeMillis();
//        t1.start();
//        t2.start();
//        ThreadUtils.join(t1, t2);
//        System.out.println(System.currentTimeMillis() - startTime);
//    }
//
//}
