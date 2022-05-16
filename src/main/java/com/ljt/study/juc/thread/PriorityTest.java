package com.ljt.study.juc.thread;

/**
 * 一般不设置线程的优先级 依赖操作系统线程优先级
 *
 * @author LiJingTang
 * @date 2020-01-03 08:39
 */
class PriorityTest {

    public static void main(String[] args) {
        PriorityThread t1 = new PriorityThread("PriorityThread - 1");
        PriorityThread t2 = new PriorityThread("PriorityThread - 2");

        t1.setPriority(Thread.NORM_PRIORITY + 3); // 优先级高的CPU分配的时间多
        t1.start();
        t2.start();
    }

    private static class PriorityThread extends Thread {

        public PriorityThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(this.getName() + " Thread loop of " + i);
            }
        }
    }

}
