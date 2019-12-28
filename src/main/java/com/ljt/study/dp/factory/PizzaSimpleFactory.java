package com.ljt.study.dp.factory;

/**
 * 简单工厂
 *
 * @author LiJingTang
 * @date 2019-12-28 20:12
 */
public class PizzaSimpleFactory {

    public static Pizza createPizza(PizzaTypeEnum type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case CHEESE:
                return new CheesePizza();
            case PEPPERONI:
                return new PepperoniPizza();
            case CLAM:
                return new ClamPizza();
            case VEGGIE:
                return new VeggiePizza();
            default:
                return null;
        }
    }

}
