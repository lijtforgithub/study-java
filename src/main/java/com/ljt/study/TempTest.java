package com.ljt.study;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    public static void main(String[] args) {
        System.out.println(RandomStringUtils.randomAlphabetic(8));
        System.out.println(RandomStringUtils.random(20, 97, 122, true, false));
    }

}
