package com.ljt.study.lang.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Timer定时器
 *
 * @author LiJingTang
 * @date 2019-11-21 20:42
 */
public class TimerTest {

    /**
     * Timer在执行定时任务时只会创建一个线程任务，如果存在多个任务， 若其中某个任务因为某种原因而导致线程任务执行时间过长，
     * 超过了两个任务的间隔时间，会和预期的执行时间不一样
     */
    @Test
    public void testTaskTime() {
        Timer timer = new Timer();
        long startTime = System.currentTimeMillis();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("firstTimerTask invoked, the time: " + (System.currentTimeMillis() - startTime));
                try {
                    TimeUnit.SECONDS.sleep(4); // 线程休眠
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("secondTimerTask invoked, the time: " + (System.currentTimeMillis() - startTime));
            }
        }, 3000);
    }

    /**
     * 不明白有什么使用场景
     */
    @Test
    public void testFixedRate() {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        System.out.println("startTime : " + localDateTime.toString().replace('T', ' '));
        Date startTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1); // 线程休眠
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LocalDateTime time = LocalDateTime.ofInstant(new Date(this.scheduledExecutionTime()).toInstant(), ZoneId.systemDefault());
                System.out.println("schedule | " + time.toString().replace('T', ' ') + "  " + LocalDateTime.now().toString().replace('T', ' '));
            }
        }, startTime, 2000);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1); // 线程休眠
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LocalDateTime time = LocalDateTime.ofInstant(new Date(this.scheduledExecutionTime()).toInstant(), ZoneId.systemDefault());
                System.out.println("scheduleAtFixedRate | " + time.toString().replace('T', ' ') + "  " + LocalDateTime.now().toString().replace('T', ' '));
            }
        }, startTime, 2000);
    }

    @Test
    public void testTaskException() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                throw new NullPointerException("执行第一个定时任务执行抛出异常！");
            }
        }, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行第二个定时任务...");
            }
        }, 2000);
    }


    /**
     * 使用线程池
     */
    @Test
    public void testTaskTimeWithPool() {
        // 一个线程的话和使用Timer一样
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        long startTime = System.currentTimeMillis();

        executorService.schedule(() -> {
            System.out.println("firstTimerTask invoked, the time: " + (System.currentTimeMillis() - startTime));
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        executorService.schedule(() -> System.out.println("secondTimerTask invoked, the time: "
                + (System.currentTimeMillis() - startTime)), 3, TimeUnit.SECONDS);
    }

    @Test
    public void testTaskExceptionWithPool() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            throw new NullPointerException("执行第一个定时任务执行抛出异常！");
        }, 1, TimeUnit.SECONDS);

        executorService.schedule(() -> System.out.println("执行第二个定时任务..."), 2, TimeUnit.SECONDS);
    }

    @AfterEach
    public void after() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
    }

}
