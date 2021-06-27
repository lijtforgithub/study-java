package com.ljt.study.juc.aqs;

import com.ljt.study.juc.ThreadUtils;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * @author LiJingTang
 * @date 2021-06-27 22:32
 */
public class PhaserTest {

    private static final MarriagePhaser PHASER = new MarriagePhaser();

    public static void main(String[] args) {
        final int num = 3;
        PHASER.bulkRegister(num + 2);

        for (int i = 0; i < num; i++) {
            new Thread(new Person("p-" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }


    private static class MarriagePhaser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了 " + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了 " + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了 " + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束 新郎新娘抱抱 " + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    @AllArgsConstructor
    private static class Person implements Runnable {

        private final String name;

        public void arrive() {
            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(name + " 到达现场");
            PHASER.arriveAndAwaitAdvance();
        }

        public void eat() {
            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(name + " 吃完");
            PHASER.arriveAndAwaitAdvance();
        }

        public void leave() {
            ThreadUtils.sleepSeconds(new Random().nextInt(10));
            System.out.println(name + " 离开");
            PHASER.arriveAndAwaitAdvance();
        }

        private void hug() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                ThreadUtils.sleepSeconds(new Random().nextInt(10));
                System.out.println(name + " 洞房");
                PHASER.arriveAndAwaitAdvance();
            } else {
                PHASER.arriveAndDeregister();
                //phaser.register()
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }

}
