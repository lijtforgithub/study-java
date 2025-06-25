package com.ljt.study.lang.jdk.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 接口的默认方法与静态方法
 *
 * @author LiJingTang
 * @date 2019-12-29 14:06
 */
public class InterfaceTest {

    public static void main(String[] args) {
        Defaultable defaulable = DefaultableFactory.create(DefaultableImpl::new);
        System.out.println(defaulable.notRequired());

        defaulable = DefaultableFactory.create(OverridableImpl::new);
        System.out.println(defaulable.notRequired());
    }

    private interface DefaultableFactory {

        static Defaultable create(Supplier<Defaultable> supplier) {
            return supplier.get();
        }
    }

    private interface Defaultable {

        default String notRequired() {
            return "Default implementation";
        }
    }

    private static class DefaultableImpl implements Defaultable {
    }

    private static class OverridableImpl implements Defaultable {

        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }

    /**
     * Consumer<T> 消费型接口（接受一个参数 无返回值）
     * param T 传入参数
     */
    @Test
    public void testConsumer() {
        Consumer<String> consumer = System.out::println; // 一个参数可以不写括号
        consumer.accept("我是消费型接口!");
    }

    /**
     * Supplier<R> 供给型接口（无参数 有返回值）
     * <p>
     * R 返回值类型
     */
    @Test
    public void testSupplier() {
        Supplier<Date> supplier = Date::new; // 没有参数 括号必须写
        System.out.println("当前时间:" + supplier.get());
    }

    /**
     * Function<T,R> 函数型接口（接受一个参数 有返回值）
     * <p>
     * T 传入参数
     * R 返回值类型
     */
    @Test
    public void testFunction() {
        Function<String, String> function = str -> "Hello " + str;
        System.out.println(function.apply("培培"));
    }

    /**
     * Predicate<T> 断定型接口（接受一个参数 返回Boolean型值）
     * <p>
     * T 传入参数
     * Boolean 返回一个Boolean型值
     */
    @Test
    public void testPredicate() {
        Predicate<Integer> predicate = num -> num % 2 == 0;
        System.out.println(predicate.test(9));
        System.out.println(predicate.test(10));
    }

    @Test
    public void testComparator() {
        Comparator<LambdaTest.User> comparator = Comparator.comparingInt(LambdaTest.User::getAge);
        LambdaTest.User user1 = new LambdaTest.User(1, "李斯", 50);
        LambdaTest.User user2 = new LambdaTest.User(2, "淳于越", 30);

        System.out.println(comparator.compare(user1, user2));
        System.out.println(comparator.reversed().compare(user1, user2));
    }

    /**
     * Optional 不是函数是接口，这是个用来防止NullPointerException异常的辅助类型
     */
    @Test
    public void testOptional() {
        Optional<String> optional = Optional.of("bam");
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("fallback"));

        optional.ifPresent(s -> System.out.println(s.charAt(0)));
    }

    /**
     * 如果Optional类的实例为非空值的话，isPresent()返回true，否从返回false。
     * 为了防止Optional为空值，orElseGet()方法通过回调函数来产生一个默认值。
     * map()函数对当前Optional的值进行转化，然后返回一个新的Optional实例。
     * orElse()方法和orElseGet()方法类似，但是orElse接受一个默认值而不是一个回调函数。
     */
    @Test
    public void testOptional2() {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElse("[none]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));

        System.out.println();

        Optional<String> firstName = Optional.of("Tom");
        System.out.println("First Name is set? " + firstName.isPresent());
        System.out.println("First Name: " + firstName.orElse("[none]"));
        System.out.println(firstName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
    }

}
