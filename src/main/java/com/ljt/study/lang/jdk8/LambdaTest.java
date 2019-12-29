package com.ljt.study.lang.jdk8;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:09
 */
public class LambdaTest {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();
    }

    @Test
    public void testMy() {
        MyFunInterface myFI = name -> "Hello Java8 : " + name;
        myFI.defaultMethod();
        System.out.println(myFI.method("糊里糊涂"));

        MyFunInterface.staticMethod();
    }

    @Test
    public void testForEach() {
        Collection<String> list = new ArrayList<>(2);
        list.add("Hello");
        list.add("World");

        list.forEach(c -> System.out.println(c));
    }

    @Test
    public void testStream() {
        Collection<User> list = new ArrayList<>(5);
        list.add(new User(1, "李斯", 50));
        list.add(new User(2, "淳于越", 30));
        list.add(new User(3, "嬴政", 35));
        list.add(new User(4, "刘邦", 40));
        list.add(new User(5, "项羽", 20));

        Collection<User> list1 = list.stream()
                .parallel()
                .filter(c -> c.getAge() > 30)
                .collect(Collectors.toList());

        list1.parallelStream()
                .filter(c -> c.getAge() < 50)
                .forEach(c -> System.out.println(c));
    }


    @FunctionalInterface
    private interface MyFunInterface {

        String method(String name);

        default void defaultMethod() {
            System.out.println("a default method");
        }

        static void staticMethod() {
            System.out.println("a static method");
        }
    }

}

class User implements Serializable {

    private static final long serialVersionUID = 2248982748978775231L;

    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
