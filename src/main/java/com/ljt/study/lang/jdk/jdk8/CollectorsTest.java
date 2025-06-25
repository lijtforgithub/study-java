package com.ljt.study.lang.jdk.jdk8;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2020-03-06 14:06
 */
public class CollectorsTest {

    @Test
    public void testToMapValue() {
        LambdaTest.User user1 = new LambdaTest.User(1, "Hello", 0);
        LambdaTest.User user2 = new LambdaTest.User(2, null, 0);
        try {
            Stream.of(user1, user2).collect(Collectors.toMap(LambdaTest.User::getId, LambdaTest.User::getName));
        } catch (Exception e) {
            System.out.println("Value 不能为 null");
            e.printStackTrace();
        }
    }

    @Test
    public void testToMapKey() {
        LambdaTest.User user1 = new LambdaTest.User(null, "Hello", 0);
        LambdaTest.User user2 = new LambdaTest.User(1, "World", 0);
        Stream.of(user1, user2).collect(Collectors.toMap(LambdaTest.User::getId, LambdaTest.User::getName));
        System.out.println("Key 可以为 null");

        try {
            user1.setId(1);
            Stream.of(user1, user2).collect(Collectors.toMap(LambdaTest.User::getId, Function.identity()));
        } catch (Exception e) {
            System.out.println("Key 不可以重复");
            e.printStackTrace();
        }
    }

}
