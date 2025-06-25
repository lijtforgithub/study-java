package com.ljt.study.lang.jdk.jdk12;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:32
 */
public class NumberFormatTest {

    @Test
    void fmt() {
        NumberFormat fmt = NumberFormat.getCompactNumberInstance(Locale.CHINESE, NumberFormat.Style.SHORT);
        System.out.println(fmt.format(1000));
        fmt = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        System.out.println(fmt.format(1000));
    }

}
