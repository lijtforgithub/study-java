package com.ljt.study.lang.jdk.jdk13;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:43
 */
public class StringTest {

    @Test
    void lines() {
        String json = """
                {
                    "name":"mkyong",
                    "age":38
                }
                """;
        System.out.println(json);

        String query = """
                SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`
                WHERE `CITY` = 'INDIANAPOLIS'
                ORDER BY `EMP_ID`, `LAST_NAME`;
                """;

        System.out.println(query);
    }

    @Test
    void formatted() {
        System.out.println("我是%s".formatted("ljt"));
    }

    @Test
    void stripIndent() {
        String text = """
                 This is an example:
                    - item 1
                    - item 2
                """;
        System.out.println(text);
        // 没看出来如何使用
        System.out.println(text.stripIndent());
    }

}
