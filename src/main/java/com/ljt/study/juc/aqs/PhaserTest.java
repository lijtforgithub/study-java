package com.ljt.study.juc.aqs;

import com.ljt.study.juc.ThreadUtils;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * @author LiJingTang
 * @date 2021-06-27 22:32
 */
public class PhaserTest {

    public static void main(String[] args) {
        final int num = 4;
        GamePhaser phaser = new GamePhaser();

        // 注册一次表示phaser维护的线程个数
        phaser.register();

        for (int i = 0; i < num; i++) {
            phaser.register();
            new Thread(new Runner(phaser)).start();
        }

        // 后续阶段主线程就不参加了
        phaser.arriveAndDeregister();
    }

    /**
     * 比赛阶段器
     */
    private static class GamePhaser extends Phaser {

        /**
         * 当一个阶段的所有线程都到达时 , 执行该方法, 此时 phase自动加1
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("预赛完成 " + registeredParties);
                    return false;
                case 1:
                    System.out.println("初赛完成 " + registeredParties);
                    return false;
                case 2:
                    System.out.println("决赛完成 " + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    /**
     * 运动员类
     */
    private static class Runner implements Runnable {

        private final Phaser phaser;

        public Runner(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(Thread.currentThread().getName() + " 参加预赛");
            phaser.arriveAndAwaitAdvance();

            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(Thread.currentThread().getName() + " 参加初赛");
            // 淘汰一个
            if (Thread.currentThread().getName().contains("1")) {
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " 参加决赛");
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

}
