package com.ljt.study.lang.jdk9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:16
 */
public class CollectionTest {

    @Test
	void of() {
		List<String> list = List.of("Java", "C++");
		System.out.println(list.getClass());
		Set<String> set = Set.of("Java", "C++");
		System.out.println(set.getClass());
		Map<String, Integer> map = Map.of("Java", 1, "C++", 2);
		System.out.println(map.getClass());

		// 不可变
//		list.add("test");
	}


}
