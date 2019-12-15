package com.ljt.study.dp.proxy;

/**
 * 静态代理 组合方式实现
 *
 * @author LiJingTang
 * @date 2019-12-14 14:38
 */
public class StaticProxy {

    public static void main(String[] args) {
        Movable tank = new Tank();
        tank.move();

        Movable proxy1 = new TankLoggerProxy(tank);
        proxy1.move();
        Movable proxy2 = new TankTimeProxy(tank);
        proxy2.move();

        new Tank2().move();
    }

    private static class TankLoggerProxy implements Movable {

        private Movable tank;

        public TankLoggerProxy(Movable tank) {
            super();
            this.tank = tank;
        }

        @Override
        public void move() {
            System.out.println("tank start...");
            tank.move();
            System.out.println("tank end...");
        }
    }

    private static class TankTimeProxy implements Movable {

        private Movable tank;

        public TankTimeProxy(Movable tank) {
            super();
            this.tank = tank;
        }

        @Override
        public void move() {
            long startTime = System.currentTimeMillis();
            tank.move();
            long endTime = System.currentTimeMillis();
            System.out.println("costTime:" + (endTime - startTime));
        }
    }

    /**
     * 继承
     */
    private static class Tank2 extends Tank {

        @Override
        public void move() {
            long startTime = System.currentTimeMillis();
            super.move();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
    }

}
