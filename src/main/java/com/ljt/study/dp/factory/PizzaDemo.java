package com.ljt.study.dp.factory;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-12-28 20:07
 */
public class PizzaDemo {

    @Test
    public void testSimple() {
        Pizza pizza = PizzaSimpleFactory.createPizza(PizzaTypeEnum.CLAM);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    @Test
    public void testMethod() {
        PizzaStore pizzaStore = new PizzaMethodFactory.NYPizzaStore();
        pizzaStore.orderPizza(PizzaTypeEnum.CHEESE);
    }

}

abstract class PizzaStore {

    public Pizza orderPizza(PizzaTypeEnum type) {
        Pizza pizza = this.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    abstract public Pizza createPizza(PizzaTypeEnum type);

}

abstract class Pizza {

    protected String name;
    protected String dough;
    protected String sauce;

    public Pizza(String name) {
        super();
        this.name = name;
    }

    public void prepare() {
        System.out.println(this.name + " prepare...");
        System.out.println("Tossing dough...");
        System.out.println("Adding sauce...");
    }

    public void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }
}

class CheesePizza extends Pizza {

    public CheesePizza() {
        super(CheesePizza.class.getSimpleName());
    }
}

class PepperoniPizza extends Pizza {

    public PepperoniPizza() {
        super(PepperoniPizza.class.getSimpleName());
    }
}

class ClamPizza extends Pizza {

    public ClamPizza() {
        super(ClamPizza.class.getSimpleName());
    }
}

class VeggiePizza extends Pizza {

    public VeggiePizza() {
        super(VeggiePizza.class.getSimpleName());
    }
}

enum PizzaTypeEnum {

    CHEESE,
    PEPPERONI,
    CLAM,
    VEGGIE;
}
