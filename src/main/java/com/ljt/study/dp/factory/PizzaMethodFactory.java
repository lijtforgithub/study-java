package com.ljt.study.dp.factory;

/**
 * 工厂方法
 *
 * @author LiJingTang
 * @date 2019-12-28 20:16
 */
public class PizzaMethodFactory {

    static class NYPizzaStore extends PizzaStore {

        @Override
        public Pizza createPizza(PizzaTypeEnum type) {
            if (type == null) {
                return null;
            }

            switch (type) {
                case CHEESE:
                    return new NYStyleCheesePizza();
                case PEPPERONI:
                    return new NYStylePepperoniPizza();
                case CLAM:
                    return new NYStyleClamPizza();
                case VEGGIE:
                    return new NYStyleVeggiePizza();
                default:
                    return null;
            }
        }
    }

    static class ChicagoPizzaStore extends PizzaStore {

        @Override
        public Pizza createPizza(PizzaTypeEnum type) {
            // TODO Auto-generated method stub
            return null;
        }
    }

    private static class NYStyleCheesePizza extends Pizza {

        public NYStyleCheesePizza() {
            super(NYStyleCheesePizza.class.getSimpleName());
        }

        @Override
        public void cut() {
            System.out.println("Cutting the pizza into square slices");
        }
    }

    private static class NYStylePepperoniPizza extends Pizza {

        public NYStylePepperoniPizza() {
            super(NYStylePepperoniPizza.class.getSimpleName());
        }
    }

    private static class NYStyleClamPizza extends Pizza {

        public NYStyleClamPizza() {
            super(NYStyleClamPizza.class.getSimpleName());
        }
    }

    private static class NYStyleVeggiePizza extends Pizza {

        public NYStyleVeggiePizza() {
            super(NYStyleVeggiePizza.class.getSimpleName());
        }
    }

}
