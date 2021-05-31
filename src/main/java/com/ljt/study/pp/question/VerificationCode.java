package com.ljt.study.pp.question;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2021-05-25 09:00
 */
public class VerificationCode {

    public static void main(String[] args) {
        int count = 100_0000;

        long start = System.currentTimeMillis();
        IntStream.rangeClosed(0, count).forEach(i -> {
            String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        });
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        IntStream.rangeClosed(0, count).forEach(i -> {
            String code = RandomStringUtils.randomAlphanumeric(6);
        });
        System.out.println(System.currentTimeMillis() - start);

    }

}
