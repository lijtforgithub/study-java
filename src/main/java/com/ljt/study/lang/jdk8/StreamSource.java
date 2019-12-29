package com.ljt.study.lang.jdk8;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:27
 */
public class StreamSource {

    private static final int[] DATA_1 = {4, 6, 5, 3, 8, 1, 2, 7};
    private static final String[] DATA_2 = {"4", "6", "5", "3", "8", "1", "2", "7"};

    @Test
    public void testArray() {
        IntStream stream = Arrays.stream(DATA_1); // 默认串行
        System.out.println("并行 = " + stream.isParallel());
        stream.parallel();                        // 并行
        System.out.println("并行 = " + stream.isParallel());
        stream.sequential().parallel().sequential().parallel(); // 以最后一个为准
        System.out.println("并行 = " + stream.isParallel());

        int[] array = stream.sorted().toArray();
        System.err.println("原数组：" + Arrays.toString(DATA_1));
        System.err.println("排序后：" + Arrays.toString(array));
    }

    @Test
    public void testCollection() {
        Stream<String> stream = Arrays.asList(DATA_2).stream();
        System.out.println("并行 = " + stream.isParallel());

        int[] array = stream.mapToInt(Integer::parseInt)
                .filter(e -> e % 2 == 0).toArray();
        System.err.println(Arrays.toString(array));
    }

    @Test
    public void testCollectionParallel() {
        Stream<String> stream = Arrays.asList(DATA_2).parallelStream();
        System.out.println("并行 = " + stream.isParallel());
        stream.sequential(); // 串行
        System.out.println("并行 = " + stream.isParallel());

        List<String> list = stream.sorted((e1, e2) -> e2.compareTo(e1))
                .skip(2).collect(Collectors.toList());
        System.err.println(list);
    }

    @Test
    public void testStreamRange() {
        IntSummaryStatistics statistics = IntStream.range(0, 100).summaryStatistics();
        System.out.println(statistics.toString());

        IntStream.range(0, 10).forEach(System.err::print);
    }

    @Test
    public void testStreamOf() {
        Stream<String> stream = Stream.of(DATA_2);

        stream.forEachOrdered(System.err::print);
    }

    @Test
    public void testIO() throws IOException {
        String filename = "D:/Workspace/IDEA/study-java/src/main/java/com/ljt/study/lang/jdk8/StreamSource" +
                ".java";
        Path path = new File(filename).toPath();

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.onClose(() -> System.err.println("文件读取完毕!"))
                    .forEach(System.out::println);
        }
    }

}
