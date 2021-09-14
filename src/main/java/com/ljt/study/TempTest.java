package com.ljt.study;

import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    @SneakyThrows
    public static void main(String[] args) {
        Set<Integer> sets = new HashSet<>();
        sets.add(1);
        sets.add(2);
        sets.add(3);

        sets.forEach(i -> {
            if (i == 2) {
                return;
            }
            System.out.println(i);
        });
    }

}
