package com.ljt.study.lang.jdk.jdk13;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:01
 */
public class SwitchYieldTest {

    @Test
    void test() {
        System.out.println(descLanguage("Java"));
    }

    /**
     * yield和 return 的区别在于：return 会直接跳出当前循环或者方法，而 yield 只会跳出当前 Switch 块，同时在使用 yield 时，需要有 default 条件
     */
    private static String descLanguage(String name) {
        String desc = switch (name) {
            case "Java": yield "object-oriented, platform independent and secured";
            case "Ruby": yield "a programmer's best friend";
            default: yield name +" is a good language";
        };
        System.out.println("return");
        return desc;
    }

}
