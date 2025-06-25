package com.ljt.study.lang.jdk.jdk17;

import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * @author LiJingTang
 * @date 2025-06-25 15:30
 */
public class RandomTest {

    @Test
    void test() {
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        // 生成随机数
        System.out.println(randomGenerator.nextInt(10));
        System.out.println(randomGenerator.nextInt(10));
        System.out.println(randomGenerator.nextInt(10));
        System.out.println(randomGenerator.nextInt(10));
    }

}
