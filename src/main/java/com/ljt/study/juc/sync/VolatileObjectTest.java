package com.ljt.study.juc.sync;

import com.ljt.study.juc.ThreadUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LiJingTang
 * @date 2021-08-30 21:19
 */
class VolatileObjectTest {

    public static void main(String[] args) {
        VolatileObjectTest test = new VolatileObjectTest();
        VolatileObjectTest.VolatileThread t = test.new VolatileThread();
        t.start();
        ThreadUtils.sleepSeconds(1);
        System.out.println("wakeup");
        // 有volatile属性发生变好也会使缓存行失效 保证可见行
        test.f.setF(0);
    }

    private
    volatile
    Flag f = new Flag();

    private class VolatileThread extends Thread {

        @Override
        public void run() {
            System.out.println("start");
            while (f.getF() == 1) {
            }
            System.out.println("end");
        }
    }

    @Getter
    @Setter
    static class Flag {
        int f = 1;
    }

}
