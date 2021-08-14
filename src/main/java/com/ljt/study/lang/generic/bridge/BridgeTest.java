package com.ljt.study.lang.generic.bridge;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 为了兼容泛型1.5之前的方法 编译时会生成Bridge Synthetic 类型方法
 * @author LiJingTang
 * @date 2021-08-14 09:55
 */
class BridgeTest {

    public static void main(String[] args) {
        SuperClass obj = new ChildClass();
        obj.method("Bridge");
        // 能编译通过 运行时候异常 java.lang.Object cannot be cast to java.lang.String
        obj.method(new Object());
    }

    @Test
    void getMethods() {
        Class<ChildClass> clazz = ChildClass.class;
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            System.out.printf("%s %s %s %s \n", method.getName(), Arrays.toString(method.getParameterTypes()), method.isBridge(),
                    method.isSynthetic());
        }
    }

}
