package com.ljt.study.pp.effective.chap04;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-12-29 10:52
 */
public class Item13 {

    private static final Object[] PRIVATE_VALUES = {};
    public static final List<Object> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

    private static final Object[] PRIVATE_VALUES_2 = {};

    public static Object[] values() {
        return PRIVATE_VALUES_2.clone();
    }

}
