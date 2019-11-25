package com.ljt.study.lang.type.annotation;

import com.ljt.study.lang.type.enums.TrafficLampEnum;

import java.lang.annotation.*;

/**
 * @author LiJingTang
 * @date 2019-11-23 11:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface MyAnnotation {

    public String name() default "Lijt"; // 字符串属性 默认值用default设置

    public int value(); // 属性value调用的地方可以不用写value=

    public Class<?> clazz() default Class.class; // 字节码属性

    public String[] arrayAttr() default {}; // 数组属性

    public TrafficLampEnum enumAttr() default TrafficLampEnum.RED; // 枚举属性

    // 包级使用的属性
    AttrAnnotation annotationAttr() default @AttrAnnotation({}); // 注解属性@MetaAnnotation就是一个对象了

}
