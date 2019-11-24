package com.ljt.study.lang.grammar;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author LiJingTang
 * @date 2019-11-22 17:23
 */
public class Array {

    /**
     * asList接受的参数是一个泛型的变长参数，基本数据类型是无法型化的，也就是说8个基本类型是无法作为asList的参数的，
     * 要想作为泛型参数就必须使用其所对应的包装类型。在Java中数组是一个对象，它是可以泛型化的。因此基本数据类型数组是作为一个参数传进去的。
     *
     * public static List asList(Object[] a) - JDK1.4
     * public static <T> List<T> asList(T... a) - JDK1.5
     *
     * 如果是基本类型的数组则会用JDK1.5的语法作为一个参数放进List，所以打印的话会是一个数组地址。如：[[I@1c313da]
     * 如果是引用类型的话会兼容JDK1.4的语法，如果是字符串，打印的如：[hello, world, from, java]
     */
    @Test
    public void asList () {
        int[] intArray = {1, 2, 3, 4, 5};
        List<int[]> intList = Arrays.asList(intArray);
        System.out.println(intList + " - " + intList.size());

        Integer[] integerArray = {1, 2, 3, 4, 5};
        List<Integer> integerList = Arrays.asList(integerArray);
        System.out.println(integerList + " - " + integerList.size());
    }

    /**
     * asList返回的ArrayList不是java.util.ArrayList，他是Arrays的内部类。
     * 该内部类提供了size、toArray、get、set、indexOf、contains方法，而像add、remove等改变list结果的方法从AbstractList父类继承过来，
     * 未重写，它直接抛出UnsupportedOperationException异常
     */
    @Test
    public void asListUnmodifiable () {
        Integer[] array = {1, 2, 3, 4, 5};
        List<Integer> list = Arrays.asList(array);
        list.add(6);
    }

    /**
     * 数组的类名由若干个'['和数组元素类型的内部名称组成，'['的数目代表了数组的维度。
     * 具有相同类型元素和相同维度的数组，属于同一个类。
     * 如果两个数组的元素类型相同，但维度不同，那么它们也属于不同的类。
     * 如果两个数组的元素类型和维度均相同，但长度不同，那么它们还是属于同一个类。
     */
    @Test
    public void testType() {
        int[] a = new int[10];
        int[][] b = new int[10][10];
        System.out.println(a.getClass().getName());
        System.out.println(b.getClass().getName());
        System.out.println(a.getClass().equals(b.getClass()));
        System.out.println(a.getClass().equals(new int[1].getClass()));
    }

    /**
     * 可见，[I这个类是java.lang.Object的直接子类，自身没有声明任何成员变量、成员方法、构造函数和Annotation，可以说，[I就是个空类。
     * 连length这个成员变量都没有。
     * 类加载器先看看数组类是否已经被创建了。如果没有，那就说明需要创建数组类；如果有，那就无需创建了。
     * 如果数组元素是引用类型，那么类加载器首先去加载数组元素的类。
     * JVM根据元素类型和维度，创建相应的数组类。
     */
    @Test
    public void testAttr() {
        int[] a = new int[10];
        Class<?> clazz = a.getClass();
        System.out.println(clazz.getDeclaredFields().length);
        System.out.println(clazz.getDeclaredMethods().length);
        System.out.println(clazz.getDeclaredConstructors().length);
        System.out.println(clazz.getDeclaredAnnotations().length);
        System.out.println(clazz.getDeclaredClasses().length);
        System.out.println(clazz.getSuperclass());
    }

    @Test
    public void testLoopCost() {
        int len = 100_0000;
        Object[] array = new Object[len];
        Object obj = null;

        for (int i = 0; i < len; i++) {
            array[i] = new Object();
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            obj = array[i];
        }
        System.out.println(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        for (Object o : array) {
            obj = o;
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(obj);
    }


    // 声明一个数组
    @Test
    public void testNew() {
        String[] aArray = new String[5];
        String[] bArray = {"a", "b", "c", "d", "e"};
        String[] cArray = new String[] {"a", "b", "c", "d", "e"};
    }

    // 输出一个数组
    @Test
    public void printArray() {
        int[] array = {1, 2, 3, 4, 5};
        String s = Arrays.toString(array);
        System.out.println(array);
        System.out.println(s);
    }

    // 从一个数组创建数组列表
    @Test
    public void arrayToCollection() {
        String[] array = {"a", "b", "c", "d", "e"};
        List<String> list = new ArrayList<>(Arrays.asList(array));
        System.out.println(list);
    }

    // 检查一个数组是否包含某个值
    @Test
    public void contains() {
        String[] array = {"a", "b", "c", "d", "e"};
        System.out.println(Arrays.asList(array).contains("a"));
    }

    // 连接两个数组
    @Test
    public void arrayAdd() {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {6, 7, 8, 9, 10};
        System.out.println(ArrayUtils.addAll(array1, array2));
    }

    // 把提供的数组元素放入一个字符串
    @Test
    public void arrayToString() {
        System.out.println(StringUtils.join(new String[] {"a", "b", "c"}, ", "));
    }

    // 将一个数组列表转换为数组
    @Test
    public void collectionToArray() {
        String[] array = {"a", "b", "c", "d", "e"};
        List<String> list = new ArrayList<>(Arrays.asList(array));
        String[] tempArray = new String[list.size()];
        list.toArray(tempArray);

        for (String s : tempArray)
            System.out.println(s);
    }

    // 将一个数组转换为集（set）
    @Test
    public void arrayToSet() {
        String[] array = {"a", "b", "c", "d", "e"};
        Set<String> set = new HashSet<>(Arrays.asList(array));
        System.out.println(set);
    }

    // 逆向一个数组
    @Test
    public void reverse() {
        int[] array = {1, 2, 3, 4, 5};
        ArrayUtils.reverse(array);
        System.out.println(Arrays.toString(array));
    }

    // 移除数组中的元素
    @Test
    public void remove() {
        int[] array = {1, 2, 3, 4, 5};
        int[] removed = ArrayUtils.removeElement(array, 3); // create a new array
        System.out.println(Arrays.toString(removed));
    }

    // 将整数转换为字节数组
    @Test
    public void intToByteArray() {
        byte[] bytes = ByteBuffer.allocate(4).putInt(8).array();

        for (byte t : bytes)
            System.out.format("0x%x ", t);
    }

}
