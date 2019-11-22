package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:36
 */
public class ClassTest {

    @Test
    public void testMethod() {
        Object obj = ClassTest.class.cast(new ClassTest()); // 将一个对象强制转换成此 Class对象所表示的类或接口
        System.out.println("规范化名称: " + obj.getClass().getCanonicalName()); // 返回 Java语言规范中所定义的底层类的规范化名称

        System.out.println("断言状态: " + ClassTest.class.desiredAssertionStatus());
        System.out.println("是否是枚举类型：" + ClassTest.class.isEnum()); //
        System.out.println(ClassTest.class.getEnumConstants());
        System.out.println(City.class.isEnum());
        System.out.println("枚举类的元素: " + Arrays.toString(City.class.getEnumConstants()));

    }

    /**
     * 作用是将调用这个方法的class对象转换成由clazz参数所表示的class对象的某个子类
     */
    @Test
    public void testAsSubclass() throws Exception {
        List<?> list = new ArrayList<>();
        Class<?> castList = list.getClass().asSubclass(List.class);

        System.out.println(list.getClass());
        System.out.println(castList.newInstance().getClass().getName());
        System.out.println(Class.forName("java.util.ArrayList").asSubclass(List.class).newInstance().getClass().getSimpleName());
    }

    private enum City {
        上海, 北京, 广州, 深圳, 天津
    }

}
