package com.ljt.study.lang.jdk.jdk12;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.MONDAY;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:37
 */
public class SwitchTest {

    /**
     * 使用类似 lambda 语法条件匹配成功后的执行块，不需要多写 break 。
     */
    @Test
    void test() {
        DayOfWeek day = MONDAY;
        switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
            case TUESDAY                -> System.out.println(7);
            case THURSDAY, SATURDAY     -> System.out.println(8);
            case WEDNESDAY              -> System.out.println(9);
        }
    }
}
