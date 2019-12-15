package com.ljt.study.dp.template;

/**
 * @author LiJingTang
 * @date 2019-12-14 16:40
 */
public class Coffee extends CaffeineBeverage {

    @Override
    void brew() {
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding Sugar and Milk");
    }

}

abstract class CaffeineBeverage {

    final public void prepareRecipe() {
        this.boilWater();
        this.brew();
        this.popurInCup();
        this.addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("Boiling water");
    }

    void popurInCup() {
        System.out.println("Pouring into cup");
    }
}
