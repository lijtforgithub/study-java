package com.ljt.study.agent;

import java.util.concurrent.TimeUnit;

/**
 *  被agent类程序入口
 *
 * @author LiJingTang
 * @date 2019-11-21 09:31
 */
public class AgentTest {

    public static void main(String[] args) {
        System.out.printf("%s => main方法 \n", AgentTest.class.toString());

        new Thread(() ->  {
            while (true) {
                AgentTest.sayHello();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                AgentTest.sayBye();
            }
        }).start();
    }

    private static void sayHello() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World");
    }

    private static void sayBye() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Bye World");
    }

}
