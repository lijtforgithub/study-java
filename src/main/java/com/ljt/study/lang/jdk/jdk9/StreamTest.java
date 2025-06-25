package com.ljt.study.lang.jdk.jdk9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:30
 */
public class StreamTest {


	@Test
	void ofNullable() {
		Stream<String> stringStream = Stream.ofNullable("Java");
		System.out.println(stringStream.count());
		Stream<String> nullStream = Stream.ofNullable(null);
		System.out.println(nullStream.count());
	}

	/**
	 * takeWhile方法可以从 Stream 中依次获取满足条件的元素，直到不满足条件为止结束获取。
	 */
	@Test
	void takeWhile() {
		List<Integer> list = List.of(11, 33, 66, 8, 9, 13);
		list.stream().takeWhile(x -> x < 50).forEach(System.out::println);
	}

	/**
	 * dropWhile方法可以从 Stream 中依次删除满足条件的元素，直到不满足条件为止结束获取。
	 */
	@Test
	void dropWhile() {
		List<Integer> list = List.of(11, 33, 66, 8, 9, 13);
		list.stream().dropWhile(x -> x < 50).forEach(System.out::println);
	}

    @Test
	void iterate() {
		IntStream.iterate(1, i -> i < 10, i -> i + 1).forEach(System.out::println);
	}

}
