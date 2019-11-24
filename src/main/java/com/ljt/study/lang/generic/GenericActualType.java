package com.ljt.study.lang.generic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取泛型实际类型
 *
 * @author LiJingTang
 * @date 2019-11-23 14:38
 */
public class GenericActualType {

    private List<String> list;
    private Map<Long, Boolean> map;

    private void m(List<Date> dateList) {

    }

    @Test
    public void getFieldActualType() throws NoSuchFieldException {
        printActualType(GenericActualType.class.getDeclaredField("list").getGenericType());
        printActualType(GenericActualType.class.getDeclaredField("map").getGenericType());
    }

    @Test
    public void getMethodActualType() throws NoSuchMethodException {
        Method method = GenericActualType.class.getDeclaredMethod("m", List.class);
        for (Type type : method.getGenericParameterTypes()) {
            printActualType(type);
        }
    }

    @Test
    public void getClassActualType() {
    }

    @Test
    public void getInterfaceActualType() {
        Type[] types = GenericInterface.ChildClass.class.getGenericInterfaces();
        for (Type type : types) {
            printActualType(type);
        }
    }

    private static void printActualType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            System.out.println("RawType = " + paramType.getRawType());
            Type[] actualTypes = paramType.getActualTypeArguments();

            for (Type aType : actualTypes) {
                if (aType instanceof Class) {
                    Class<Type> clazz = (Class<Type>) aType;
                    System.out.println("ActualType = " + clazz.getName());
                }
            }
        } else {
            System.err.println("不是泛型类型：" + type);
        }
    }

}
