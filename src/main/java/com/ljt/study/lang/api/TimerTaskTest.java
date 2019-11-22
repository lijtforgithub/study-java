package com.ljt.study.lang.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2019-11-21 17:12
 */
public class TimerTaskTest {

    @Test
    public void testTask1() {
        new MyTimerTask1();
    }

    @Test
    public void testTask2() {
        new MyTimerTask2(3);
    }

    @Test
    public void testTask3() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        new MyTimerTask3(calendar.getTime());
    }

    @Test
    public void testTask4() {
        new MyTimerTask4();
    }

    @AfterEach
    public void after() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
    }


    /**
     * 固定频率执行
     */
    private static class MyTimerTask1 extends TimerTask {

        public MyTimerTask1() {
            new Timer().schedule(this, 2000);
        }

        @Override
        public void run() {
            System.out.println("MyTimerTask-1 UP!");
            // 设置下一次定时任务
            new MyTimerTask1();
        }
    }

    /**
     * 指定延迟时间点执行
     */
    private static class MyTimerTask2 extends TimerTask {

        public MyTimerTask2(int second) {
            new Timer().schedule(this, second * 1000);
        }

        @Override
        public void run() {
            System.out.println("MyTimerTask-2 UP!");
        }
    }

    /**
     * 指定时间点执行
     */
    private static class MyTimerTask3  extends TimerTask {

        public MyTimerTask3(Date date) {
            new Timer().schedule(this, date);
        }

        @Override
        public void run() {
            System.out.println("MyTimerTask-3 UP!");
        }
    }

    private static class MyTimerTask4 extends TimerTask {

        public MyTimerTask4() {
            new Timer().schedule(this, 1000, 2000);
        }

        @Override
        public void run() {
            System.out.println("本次执行任务的时间为：" + new Date(this.scheduledExecutionTime()));
        }
    }

}
