package com.ljt.study.dp.factory;

/**
 * @author LiJingTang
 * @date 2019-12-28 20:25
 */
public class PizzaAbstractFactory {

    /**
     * Pizza 原料工厂
     */
    private interface PizzaIngredientFactory {

        Dough createDough();

        Sauce createSauce();

        Cheese createCheese();

        Veggies[] createVeggies();

        Pepperoni createPepperoni();

        Clams createClam();

    }

    private static class Dough {

    }

    private static class Sauce {

    }

    private static class Cheese {

    }

    private static class Veggies {

    }

    private static class Pepperoni {

    }

    private static class Clams {

    }

}
