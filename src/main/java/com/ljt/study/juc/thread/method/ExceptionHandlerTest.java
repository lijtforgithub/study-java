package com.ljt.study.juc.thread.method;

/**
 * @author LiJingTang
 * @date 2024-03-20 14:12
 */
class ExceptionHandlerTest {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        Thread t = new Thread(new ExceptionTask(), "exception-thread");
        t.start();
    }

    private static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(String.format("线程%s发现未处理异常 %s", t.getName(), e.getMessage()));
        }
    }

    private static class ExceptionTask implements Runnable {

        @Override
        public void run() {
            if (1 > 0) {
                throw new IllegalArgumentException("模拟异常");
            }
        }

    }

}
