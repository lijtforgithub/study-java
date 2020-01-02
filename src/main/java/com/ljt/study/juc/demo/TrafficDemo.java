package com.ljt.study.juc.demo;

import com.ljt.study.juc.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2020-01-02 17:25
 */
public class TrafficDemo {

    public static void main(String[] args) {
        String[] directions = new String[]{
                Lamp.S2N.toString(), Lamp.S2W.toString(), Lamp.E2W.toString(), Lamp.E2S.toString(),
                Lamp.N2S.toString(), Lamp.N2E.toString(), Lamp.W2E.toString(), Lamp.W2N.toString(),
                Lamp.S2E.toString(), Lamp.E2N.toString(), Lamp.N2W.toString(), Lamp.W2S.toString()
        };

        for (String name : directions) {
            new Road(name);
        }

        new LampController();
    }

    private static class Road {

        private String name;
        private List<String> vehicleList;

        public Road(String name) {
            super();
            this.name = name;
            this.vehicleList = new ArrayList<>(1000);

            ExecutorService threadPool = Executors.newSingleThreadExecutor();
            threadPool.execute(() -> {
                for (int i = 0; i < 1000; i++) {
                    ThreadUtils.sleepSeconds(new Random().nextInt(10) + 1);
                    vehicleList.add(Road.this.name + "_" + (i + 1));
                }
            });

            ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
            timer.scheduleAtFixedRate(() -> {
                if (!vehicleList.isEmpty()) {
                    boolean lighted = Lamp.valueOf(Road.this.name).isLighted(); // 交通灯是绿灯为亮

                    if (lighted) {
                        System.out.println(vehicleList.remove(0) + " is traversing...");
                    }
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }

    private static class LampController {

        private Lamp currentLamp;

        public LampController() {
            this.currentLamp = Lamp.S2N;
            this.currentLamp.light();

            ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
            timer.scheduleAtFixedRate(() -> {
                currentLamp = currentLamp.unLight(); // 每10秒当前灯绿灯灭 返回下一个绿灯
            }, 10, 10, TimeUnit.SECONDS);
        }
    }

    private enum Lamp {

        S2N("N2S", "S2W", false), // 南->北 直行
        S2W("N2E", "E2W", false), // 南->西 左转弯
        E2W("W2E", "E2S", false), // 东->西 直行
        E2S("W2N", "S2N", false), // 东->南 左转弯
        N2S(null, null, false), // 北->南 直行
        N2E(null, null, false), // 北->东 左转弯
        W2E(null, null, false), // 西->东 直行
        W2N(null, null, false), // 西->北 左转弯
        S2E(null, null, true), // 南->东 右转弯
        E2N(null, null, true), // 东->北 右转弯
        N2W(null, null, true), // 北->西 右转弯
        W2S(null, null, true); // 西->南 右转弯

        private Lamp() {
        }

        private Lamp(String opposite, String next, boolean lighted) {
            this.opposite = opposite;
            this.next = next;
            this.lighted = lighted;
        }

        private boolean lighted; // 绿灯就是亮的
        private String opposite; // 相对方向上的灯
        private String next;

        public boolean isLighted() {
            return this.lighted;
        }

        public void light() {
            this.lighted = true;

            if (null != this.opposite) {
                Lamp.valueOf(this.opposite).light();
            }

            System.out.println(this.name() + " lamp is green, 下面总共应该有6个方向能看到汽车穿过...");
        }

        public Lamp unLight() {
            this.lighted = false;

            if (null != this.opposite) {
                Lamp.valueOf(this.opposite).unLight();
            }

            Lamp nextLamp = null;

            if (null != this.next) {
                nextLamp = Lamp.valueOf(this.next);
                System.out.println("绿灯从 " + this.name() + " 切换为 " + this.next);
                nextLamp.light();
            }

            return nextLamp;
        }
    }

}
