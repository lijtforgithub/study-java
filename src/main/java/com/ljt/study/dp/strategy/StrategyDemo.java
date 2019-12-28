package com.ljt.study.dp.strategy;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-12-10 15:50
 */
public class StrategyDemo {

    @Test
    public void test_MallardDuck() {
        Duck duck = new MallardDuck();
        duck.display();
        duck.performFly();
        duck.performQuack();
    }

    @Test
    public void test_ModelDuck() {
        Duck duck = new ModelDuck();
        duck.display();
        duck.performFly();
        duck.setFlyBehavior(new FlyRocketPowered());
        duck.performFly();
    }


    private abstract static class Duck {

        protected FlyBehavior flyBehavior;
        protected QuackBehavior quackBehavior;

        public abstract void display();

        public void performFly() {
            flyBehavior.fly();
        }

        public void performQuack() {
            quackBehavior.quack();
        }

        public void swim() {
            System.out.println("All ducks float, even decoys!");
        }

        public FlyBehavior getFlyBehavior() {
            return flyBehavior;
        }

        public void setFlyBehavior(FlyBehavior flyBehavior) {
            this.flyBehavior = flyBehavior;
        }

        public QuackBehavior getQuackBehavior() {
            return quackBehavior;
        }

        public void setQuackBehavior(QuackBehavior quackBehavior) {
            this.quackBehavior = quackBehavior;
        }
    }

    private static class MallardDuck extends Duck {

        public MallardDuck() {
            quackBehavior = new Quack();
            flyBehavior = new FlyWithWings();
        }

        @Override
        public void display() {
            System.out.println("I'm a real Mallard duck");
        }
    }

    private static class ModelDuck extends Duck {

        public ModelDuck() {
            flyBehavior = new FlyNoWay();
            quackBehavior = new Quack();
        }

        @Override
        public void display() {
            System.out.println("I'm a model duck");
        }
    }

    private static class MuteQuack implements QuackBehavior {

        @Override
        public void quack() {
            System.out.println("<< Silence >>");
        }
    }


    private interface FlyBehavior {

        void fly();
    }

    private static class FlyNoWay implements FlyBehavior {

        @Override
        public void fly() {
            System.out.println("I can't fly");
        }
    }

    private static class FlyRocketPowered implements FlyBehavior {

        @Override
        public void fly() {
            System.out.println("I'm flying with a rocket!");
        }
    }

    private static class FlyWithWings implements FlyBehavior {

        @Override
        public void fly() {
            System.out.println("I'm flying!!");
        }
    }


    private interface QuackBehavior {

        void quack();
    }

    private static class Quack implements QuackBehavior {

        @Override
        public void quack() {
            System.out.println("Quack");
        }
    }

    private static class Squeak implements QuackBehavior {

        @Override
        public void quack() {
            System.out.println("Squeak");
        }
    }


}
