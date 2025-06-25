package com.ljt.study.lang.jdk.jdk10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:24
 */
public class CollectionTest {

    @Test
    void copyOf() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        List<Integer> listCopy = List.copyOf(list);
        System.out.println(list.equals(listCopy));
        list.add(2);
        System.out.println(listCopy);
//        listCopy.add(2);
    }

    @Test
    void toUnmodifiable() {
        var list = new ArrayList<>();
        List<Object> listCopy = list.stream().collect(Collectors.toUnmodifiableList());
        Set<Object> setCopy = list.stream().collect(Collectors.toUnmodifiableSet());
    }

}
