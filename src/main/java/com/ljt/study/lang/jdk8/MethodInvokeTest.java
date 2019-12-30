package com.ljt.study.lang.jdk8;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * 方法引用
 *
 * @author LiJingTang
 * @date 2019-12-29 13:51
 */
public class MethodInvokeTest {

    public static void main(String[] args) {
        Car car = Car.create(Car::new); // 构造器引用：语法Class::new 或者更一般的Class<T>::new
        List<Car> cars = Collections.singletonList(car);

        cars.forEach(System.out::println);

        cars.forEach(Car::collide); // 静态方法引用：语法Class::static_method

        cars.forEach(Car::repair); // 对象的方法引用：语法Class::method

        Car police = Car.create(Car::new);
        cars.forEach(police::follow); // 特定对象的方法引用：语法instance::method
    }

    private static class Car {

        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("Collided " + car.toString());
        }

        public void follow(final Car another) {
            System.out.println("Following the " + another.toString());
        }

        public void repair() {
            System.out.println("Repaired " + this.toString());
        }

    }

}
