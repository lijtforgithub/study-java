package com.ljt.study.juc.demo;

import com.ljt.study.juc.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2020-01-02 17:00
 */
public class BankDemo {

    public static void main(String[] args) {
        // 产生4个普通窗口
        for (int i = 1; i < 5; i++) {
            ServiceWindow window = new ServiceWindow();
            window.setWindowId(i);
            window.start();
        }

        // 产生1个快速窗口
        ServiceWindow expressWindow = new ServiceWindow();
        expressWindow.setType(CustomerType.EXPRESS);
        expressWindow.start();

        // 产生1个VIP窗口
        ServiceWindow vipWindow = new ServiceWindow();
        vipWindow.setType(CustomerType.VIP);
        vipWindow.start();

        // 普通客户拿号
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            Integer serviceNumber = NumberMachine.getInstance().getCommonManager().generateNewNumber();
            System.out.println("第 " + serviceNumber + " 号普通客户正在等待服务");
        }, 0, COMMON_CUSTOMER_INTERVAL_TIME, TimeUnit.SECONDS);

        // 快速客户拿号
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            Integer serviceNumber = NumberMachine.getInstance().getExpressManager().generateNewNumber();
            System.out.println("第 " + serviceNumber + " 号快速客户正在等待服务");
        }, 0, COMMON_CUSTOMER_INTERVAL_TIME * 2, TimeUnit.SECONDS);

        // VIP客户拿号
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            Integer serviceNumber = NumberMachine.getInstance().getVipManager().generateNewNumber();
            System.out.println("第 " + serviceNumber + " 号VIP客户正在等待服务");
        }, 0, COMMON_CUSTOMER_INTERVAL_TIME * 6, TimeUnit.SECONDS);
    }

    private static final int MIN_SERVICE_TIME = 1000;
    private static final int MAX_SERVICE_TIME = 10000;

    private static final long COMMON_CUSTOMER_INTERVAL_TIME = 1;

    private static class ServiceWindow {

        private int windowId = 1;
        private CustomerType type = CustomerType.COMMON;

        public void start() {
            Executors.newSingleThreadExecutor().execute(() -> {
                while (true) {
                    switch (type) {
                        case COMMON:
                            commonService();
                            break;
                        case EXPRESS:
                            expressService();
                            break;
                        case VIP:
                            vipService();
                            break;
                    }
                }
            });
        }

        private void commonService() {
            String windowName = "第 " + this.windowId + " 号" + this.type + "窗口";
            System.out.println(windowName + "正在获取任务");
            Integer serviceNumber = NumberMachine.getInstance().getCommonManager().fetchServiceNumber();

            if (null != serviceNumber) {
                System.out.println(windowName + "开始为第" + serviceNumber + "号普通客户服务");
                long beginTime = System.currentTimeMillis();
                int maxRand = MAX_SERVICE_TIME - MIN_SERVICE_TIME;
                long serviceTime = new Random().nextInt(maxRand) + 1 + MIN_SERVICE_TIME;
                ThreadUtils.sleep(serviceTime, TimeUnit.NANOSECONDS);
                long costTime = System.currentTimeMillis() - beginTime;
                System.out.println(windowName + "为第" + serviceNumber + "个普通客户完成服务，耗时" + costTime / 1000 + "秒");
            } else {
                System.out.println(windowName + "没有取到普通任务，先休息一秒");
                ThreadUtils.sleepSeconds(1);
            }
        }

        private void expressService() {
            String windowName = "第 " + this.windowId + " 号" + this.type + "窗口";
            System.out.println(windowName + "正在获取任务");
            Integer serviceNumber = NumberMachine.getInstance().getExpressManager().fetchServiceNumber();

            if (null != serviceNumber) {
                System.out.println(windowName + "开始为第" + serviceNumber + "号快速客户服务");
                long serviceTime = MIN_SERVICE_TIME;
                ThreadUtils.sleep(serviceTime, TimeUnit.NANOSECONDS);
                System.out.println(windowName + "为第 " + serviceNumber + " 号快速客户完成服务，耗时" + serviceTime / 1000 + "秒");
            } else {
                System.out.println(windowName + "没有取到快速任务");
                commonService();
            }
        }

        private void vipService() {
            String windowName = "第 " + this.windowId + " 号" + this.type + "窗口";
            System.out.println(windowName + "正在获取任务");
            Integer serviceNumber = NumberMachine.getInstance().getVipManager().fetchServiceNumber();

            if (null != serviceNumber) {
                System.out.println(windowName + "开始为第" + serviceNumber + "号快速客户服务");
                long beginTime = System.currentTimeMillis();
                int maxRand = MAX_SERVICE_TIME - MIN_SERVICE_TIME;
                long serviceTime = new Random().nextInt(maxRand) + 1 + MIN_SERVICE_TIME;
                ThreadUtils.sleep(serviceTime, TimeUnit.NANOSECONDS);
                long costTime = System.currentTimeMillis() - beginTime;
                System.out.println(windowName + "为第 " + serviceNumber + " 号VIP客户完成服务，耗时" + costTime / 1000 + "秒");
            } else {
                System.out.println(windowName + "没有取到VIP任务");
                commonService();
            }
        }

        public void setWindowId(int windowId) {
            this.windowId = windowId;
        }

        public void setType(CustomerType type) {
            this.type = type;
        }
    }

    private static class NumberMachine {

        private NumberManager commonManager = new NumberManager();
        private NumberManager expressManager = new NumberManager();
        private NumberManager vipManager = new NumberManager();
        private static final NumberMachine INSTANCE = new NumberMachine();

        private NumberMachine() {
        }

        public static NumberMachine getInstance() {
            return INSTANCE;
        }

        public NumberManager getCommonManager() {
            return commonManager;
        }

        public NumberManager getExpressManager() {
            return expressManager;
        }

        public NumberManager getVipManager() {
            return vipManager;
        }
    }

    private static class NumberManager {

        private int lastNumber = 1;
        private List<Integer> queueList = new ArrayList<>();

        public synchronized Integer generateNewNumber() {
            queueList.add(lastNumber);
            return lastNumber++;
        }

        public synchronized Integer fetchServiceNumber() {
            if (!queueList.isEmpty()) {
                return queueList.remove(0);
            }

            return null;
        }
    }

    private enum CustomerType {

        COMMON, EXPRESS, VIP;

        @Override
        public String toString() {
            switch (this) {
                case COMMON:
                    return "普通";
                case EXPRESS:
                    return "快速";
                case VIP:
                    return "VIP";
            }

            return null;
        }
    }

}
