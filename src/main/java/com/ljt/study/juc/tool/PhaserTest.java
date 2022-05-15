package com.ljt.study.juc.tool;

import com.ljt.study.juc.ThreadUtils;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * @author LiJingTang
 * @date 2021-06-27 22:32
 */
class PhaserTest {

    private static final String NAME = "张三";

    public static void main(String[] args) {
        final int num = 4;
        GamePhaser phaser = new GamePhaser();

        phaser.register();
        new Thread(new SportsMan(phaser, NAME)).start();

        for (int i = 1; i < num; i++) {
            // 注册一次表示phaser维护的线程个数
            phaser.register();
            new Thread(new SportsMan(phaser, "某-" + i)).start();
        }
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
    private static class SportsMan implements Runnable {

        private final Phaser phaser;
        private final String name;

        public SportsMan(Phaser phaser, String name) {
            this.phaser = phaser;
            this.name = name;
        }

        @Override
        public void run() {
            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(name + " 参加预赛");
            phaser.arriveAndAwaitAdvance();

            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(name + " 参加初赛");
            // 淘汰一个
            if (NAME.equals(name)) {
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(name + " 参加决赛");
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

}
