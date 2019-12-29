package com.ljt.study.lang.jdk8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 在Java 8中，Base64编码已经成为Java类库的标准。
 *
 * @author LiJingTang
 * @date 2019-12-29 14:01
 */
public class Base64Test {

    public static void main(String[] args) {
        String text = "Base64 finally in Java 8!";

        String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        final String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println(decoded);
    }

}
