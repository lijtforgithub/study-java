package com.ljt.study.lang.type.annotation;

import com.ljt.study.lang.type.enums.TrafficLampEnum;

import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-11-23 10:59
 */
@MyAnnotation(name = "LiJingTang", value = 0, clazz = AnnotationTest.class, arrayAttr = "李敬堂", enumAttr =
        TrafficLampEnum.GREEN, annotationAttr = @AttrAnnotation({"Hello", "World"}))
public class AnnotationTest {

    @MyAnnotation(1)
    public static void main(String[] args) {
        // 默认情况为false只有加上@Retention(RetentionPolicy.RUNTIME)才能在运行时的class文件中取到
        if (AnnotationTest.class.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = AnnotationTest.class.getAnnotation(MyAnnotation.class);
            System.out.println(annotation);
            System.out.println(annotation.name());
            System.out.println(annotation.value());
            System.out.println(annotation.clazz());
            System.out.println(Arrays.asList(annotation.arrayAttr()));
            System.out.println(annotation.enumAttr().nextLamp());
            System.out.println(annotation.annotationAttr());

            // @Inherited注解的注解 子类可以继承
            System.out.println(Sub.class.getAnnotation(MyAnnotation.class));
        }
    }

    private static class Sub extends AnnotationTest {

    }

}
