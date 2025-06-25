package com.ljt.study.lang.jdk.jdk8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:31
 */
public class ParamNameTest {

    public static void main(String[] args) throws Exception {
        Method method = ParamNameTest.class.getMethod("main", String[].class);
        for (final Parameter parameter : method.getParameters()) {
            System.out.println("Parameter: " + parameter.getName());
        }
    }

}
