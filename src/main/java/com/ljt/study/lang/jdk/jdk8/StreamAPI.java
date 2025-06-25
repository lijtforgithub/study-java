package com.ljt.study.lang.jdk.jdk8;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:23
 */
public class StreamAPI {

    private static final List<String> list = Arrays.asList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
    private static final List<String> numbers = Arrays.asList("1", "2", "3", "4", "5", "6", "2", "3", "4", "5", "6", "7", "8", "4", "10");

    @Test
    public void testStream() {
        list.stream();
        list.parallelStream();
    }

    @Test
    public void testFilter() {
        list.stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
    }

    @Test
    public void testDistinct() {
        numbers.stream()
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 排序只创建了一个排列好后的Stream，而不会影响原有的数据源
     * 这也是函数式编程的一个好处：不会改变对象状态，每次都会创建一个新对象。
     */
    @Test
    public void testSort() {
        list.stream()
                .sorted()
                .filter(s -> s.startsWith("b"))
                .forEach(System.out::println);

        System.out.println(list);
    }

    @Test
    public void testCount() {
        long startsWithB = list.stream()
                .filter(s -> s.startsWith("b"))
                .count();

        System.out.println(startsWithB);
    }

    @Test
    public void testMax() {
        Optional<String> max = numbers.stream()
                .max(Comparator.comparingInt(Integer::parseInt));

        System.out.println(max.get());
    }

    @Test
    public void testCollect() {
        list.stream()
                .filter(s -> s.startsWith("b"))
                .collect(Collectors.toList()).forEach(System.out::println);
    }

    @Test
    public void testReduce() {
        Optional<String> reduced = list.stream()
                .sorted()
                .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);

        int sum = numbers.stream()
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }

    /**
     * map会将元素根据指定的Function接口来依次将元素转成另外的对象
     */
    @Test
    public void testMap() {
        list.stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
    }

    @Test
    public void testGroupingBy() {
        Map<Integer, Integer> map = numbers.stream()
                .map(Integer::valueOf)
                .filter(e -> e % 2 == 0)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(p -> 1)));

        System.out.println(map);
    }

    @Test
    public void testMatch() {
        boolean anyStartsWithA = list.stream()
                .anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartsWithA);

        boolean allStartsWithA = list.stream()
                .allMatch(s -> s.startsWith("a"));
        System.out.println(allStartsWithA);

        boolean noneStartsWithZ = list.stream()
                .noneMatch(s -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);
    }

    @Test
    public void testParallel() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);

        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long start = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println(String.format("sequential sort took: %d ms", millis));

        start = System.nanoTime();
        count = values.parallelStream().sorted().count();
        System.out.println(count);
        end = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }

    @Test
    public void testParallelSafety() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new Vector<>();
        List<Integer> list4 = new ArrayList<>();
        IntStream.range(0, 10000).forEach(list1::add);
        IntStream.range(0, 10000).parallel().forEach(list2::add);
        IntStream.range(0, 10000).parallel().forEach(list3::add);

        Lock lock = new ReentrantLock();
        lock.lock();
        IntStream.range(0, 10000).forEach(list4::add);
        lock.unlock();

        System.out.println(list1.size());
        System.out.println(list2.size());
        System.out.println(list3.size());
        System.out.println(list4.size());
    }

    /**
     * 两者完成的功能类似，主要区别在并行处理上，forEach是并行处理的，forEachOrder是按顺序处理的，显然前者速度更快。
     */
    @Test
    public void testParallelForEach() {
        String[] array = {"a", "b", "c", "d", "e"};
        Stream.of(array).parallel().forEach(System.out::print);
        System.out.println();
        Stream.of(array).parallel().forEachOrdered(System.out::print);
    }

    @Test
    public void test() {
        Collection<Streams.Task> tasks = Arrays.asList(
                new Streams.Task(Streams.Status.OPEN, 5),
                new Streams.Task(Streams.Status.OPEN, 13),
                new Streams.Task(Streams.Status.CLOSED, 8));

        long totalPointsOfOpenTasks = tasks.stream()
                .filter(task -> task.getStatus() == Streams.Status.OPEN)
                .mapToInt(Streams.Task::getPoints)
                .sum();
        System.out.println("Total points: " + totalPointsOfOpenTasks);

        double totalPoints = tasks.stream()
                .parallel()
                .map(Streams.Task::getPoints)  // or map( Task::getPoints )
                .reduce(0, Integer::sum);
        System.out.println("Total points (all tasks): " + totalPoints);

        Map<Streams.Status, List<Streams.Task>> map = tasks.stream()
                .collect(Collectors.groupingBy(Streams.Task::getStatus));
        System.out.println(map);

        Collection<String> result = tasks.stream()               // Stream<String>
                .mapToInt(Streams.Task::getPoints)               // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble(points -> points / totalPoints)     // DoubleStream
                .boxed()                                         // Stream<Double>
                .mapToLong(weight -> (long) (weight * 100))      // LongStream
                .mapToObj(percentage -> percentage + "%")        // Stream<String>
                .collect(Collectors.toList());                   // List<String>

        System.out.println(result);
    }

    /**
     * Collectors.toMap底层是基于Map.merge方法来实现的，而merge中value是不能为null的，如果为null，就会抛出空指针异常
     * value 不能为空
     */
    @Test
    public void toMap() {
        LambdaTest.User user = new LambdaTest.User(1, null, 1);
        List<LambdaTest.User> list = Collections.singletonList(user);

        Map<Object, Object> map = list.stream().collect(HashMap::new, (m, v) ->
                m.put(v.getId(), v.getName()), HashMap::putAll);
        System.out.println(map);

        list.stream().collect(Collectors.toMap(LambdaTest.User::getId, LambdaTest.User::getName));
    }


    private static class Streams {
        enum Status {
            OPEN, CLOSED
        }

        static final class Task {
            private final Status status;
            private final Integer points;

            Task(final Status status, final Integer points) {
                this.status = status;
                this.points = points;
            }

            public Integer getPoints() {
                return points;
            }

            public Status getStatus() {
                return status;
            }

            @Override
            public String toString() {
                return String.format("[%s, %d]", status, points);
            }
        }
    }

}
