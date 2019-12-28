package com.ljt.study.dp.decorator;

/**
 * @author LiJingTang
 * @date 2019-12-28 15:35
 */
public class BeverageDecorator {

    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());

        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
    }

    private static class HouseBlend extends Beverage {

        public HouseBlend() {
            super();
            this.description = "House Blend Coffee";
        }

        @Override
        public double cost() {
            return .89;
        }
    }

    private static class Espresso extends Beverage {

        public Espresso() {
            super();
            this.description = "Espresso";
        }

        @Override
        public double cost() {
            return 1.99;
        }
    }

    private static class Decaf extends Beverage {

        public Decaf() {
            super();
            this.description = "Decaf";
        }

        @Override
        public double cost() {
            return 1.05;
        }
    }

    private static class DarkRoast extends Beverage {

        public DarkRoast() {
            super();
            this.description = "Dark Roast";
        }

        @Override
        public double cost() {
            return 0.99;
        }
    }

    /**
     * 饮料
     */
    private static abstract class Beverage {

        protected String description = "Unknown Berverage";

        public String getDescription() {
            return description;
        }

        public abstract double cost();
    }

    private static abstract class CondimentDecorator extends Beverage {

        public abstract String getDescription();

    }

    private static class Mocha extends CondimentDecorator {

        private Beverage beverage;

        public Mocha(Beverage beverage) {
            super();
            this.beverage = beverage;
        }

        @Override
        public String getDescription() {
            return this.beverage.getDescription() + ", Mocha";
        }

        @Override
        public double cost() {
            return .20 + this.beverage.cost();
        }
    }

    private static class Soy extends CondimentDecorator {

        private Beverage beverage;

        public Soy(Beverage beverage) {
            super();
            this.beverage = beverage;
        }

        @Override
        public String getDescription() {
            return this.beverage.getDescription() + ", Soy";
        }

        @Override
        public double cost() {
            return .15 + this.beverage.cost();
        }
    }

    private static class Whip extends CondimentDecorator {

        private Beverage beverage;

        public Whip(Beverage beverage) {
            super();
            this.beverage = beverage;
        }

        @Override
        public String getDescription() {
            return this.beverage.getDescription() + ", Whip";
        }

        @Override
        public double cost() {
            return .1 + this.beverage.cost();
        }
    }

}
