package com.ljt.study.lang.jdk8;

/**
 * 更好的类型推测机制
 *
 * @author LiJingTang
 * @date 2019-12-29 14:03
 */
public class TypeTest {

    /**
     * Value.defaultValue()的参数类型可以被推测出，所以就不必明确给出。
     * 在Java 7中，相同的例子将不会通过编译，正确的书写方式是 Value.<String>defaultValue()。
     */
    public static void main(String[] args) {
        Value<String> value = new Value<>();
        System.out.println(value.getOrDefault("22", Value.defaultValue()));
    }

    private static class Value<T> {

        public static <T> T defaultValue() {
            return null;
        }

        public T getOrDefault(T value, T defaultValue) {
            return (value != null) ? value : defaultValue;
        }
    }

}
