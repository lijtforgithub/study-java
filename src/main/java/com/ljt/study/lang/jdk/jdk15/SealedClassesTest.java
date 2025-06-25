package com.ljt.study.lang.jdk.jdk15;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:19
 */
public class SealedClassesTest {

    /**
     * 抽象类 Person 只允许 Employee 和 Manager 继承。
     * 任何扩展密封类的类本身都必须声明为 sealed、non-sealed 或 final
     */

    public abstract sealed class Person permits Employee, Manager {

    }

    public final class Employee extends Person {
    }

    public non-sealed class Manager extends Person {
    }


//    public class User extends Person {
//    }

}
