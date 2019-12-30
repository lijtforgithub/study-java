package com.ljt.study.lang.jdk8;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 重复注解机制本身必须用@Repeatable注解。
 * Java8扩展了注解的上下文。现在几乎可以为任何东西添加注解：局部变量、泛型类、父类与接口的实现，就连方法的异常也能添加注解
 *
 * @author LiJingTang
 * @date 2019-12-29 13:54
 */
public class AnnotationTest {

    public static void main(String[] args) {
        final Holder<String> holder = new @NonEmpty Holder<>();
        @NonEmpty
        Collection<@NonEmpty String> strings = new ArrayList<>();

        for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
            System.out.println(filter.value());
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface NonEmpty {
    }

    public static class Holder<@NonEmpty T> extends @NonEmpty Object {
        public void method() throws @NonEmpty Exception {
        }
    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Filters {
        Filter[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Filters.class)
    public @interface Filter {
        String value();
    }

    @Filter("filter1")
    @Filter("filter2")
    public interface Filterable {
    }

}
