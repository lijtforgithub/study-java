package com.ljt.study.lang.jdk.jdk14;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:11
 */
public class RecordTest {

    record Person(String name, int age) {
    }

    @Test
    void test() {
        Person person = new Person("ljt", 18);
        System.out.println(person);
    }


}
