package com.ljt.study;

import lombok.SneakyThrows;

import java.util.TreeMap;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    @SneakyThrows
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("2021-08-11", 11);
        map.put("2021-08-10", 10);
        System.out.println(map);
    }

}
