package com.ljt.study.pp.question;

/**
 * @author LiJingTang
 * @date 2021-05-25 09:00
 */
public class VerificationCode {

    public static void main(String[] args) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        System.out.println(code);
    }

}
