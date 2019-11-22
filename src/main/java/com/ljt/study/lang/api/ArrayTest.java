package com.ljt.study.lang.api;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LiJingTang
 * @date 2019-11-22 17:23
 */
public class ArrayTest {

    /**
     * 数组的类名由若干个'['和数组元素类型的内部名称组成，'['的数目代表了数组的维度。
     * 具有相同类型元素和相同维度的数组，属于同一个类。
     * 如果两个数组的元素类型相同，但维度不同，那么它们也属于不同的类。
     * 如果两个数组的元素类型和维度均相同，但长度不同，那么它们还是属于同一个类。
     */
    @Test
    public void test() {
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
    public void testLoop() {
        int len = 100000;
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
    public void testSM() {
        String[] aArray = new String[5];
        String[] bArray = {"a", "b", "c", "d", "e"};
        String[] cArray = new String[] {"a", "b", "c", "d", "e"};
    }

    // 输出一个数组
    @Test
    public void testSC() {
        int[] intArray = {1, 2, 3, 4, 5};
        String intArrayString = Arrays.toString(intArray);

        System.out.println(intArray);
        System.out.println(intArrayString);
    }

    // 从一个数组创建数组列表
    @Test
    public void testCJ() {
        String[] stringArray = {"a", "b", "c", "d", "e"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(stringArray));

        System.out.println(arrayList);
    }

    // 检查一个数组是否包含某个值
    @Test
    public void testBH() {
        String[] stringArray = {"a", "b", "c", "d", "e"};
        boolean b = Arrays.asList(stringArray).contains("a");

        System.out.println(b);
    }

    // 连接两个数组
    @Test
    public void testLJ() {
        int[] intArray = {1, 2, 3, 4, 5};
        int[] intArray2 = {6, 7, 8, 9, 10};
        int[] combinedIntArray = ArrayUtils.addAll(intArray, intArray2);
    }

    // 声明一个内联数组（Array inline）
    @Test
    public void testNL() {
        // method(new String[]{"a", "b", "c", "d", "e"});
    }

    // 把提供的数组元素放入一个字符串
    @Test
    public void testSTR() {
        String j = StringUtils.join(new String[] {"a", "b", "c"}, ", ");

        System.out.println(j);
    }

    // 将一个数组列表转换为数组
    @Test
    public void testZH() {
        String[] stringArray = {"a", "b", "c", "d", "e"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(stringArray));
        String[] stringArr = new String[arrayList.size()];
        arrayList.toArray(stringArr);

        for (String s : stringArr)
            System.out.println(s);
    }

    // 将一个数组转换为集（set）
    @Test
    public void testSET() {
        String[] stringArray = {"a", "b", "c", "d", "e"};
        Set<String> set = new HashSet<String>(Arrays.asList(stringArray));

        System.out.println(set);
    }

    // 逆向一个数组
    @Test
    public void testNX() {
        int[] intArray = {1, 2, 3, 4, 5};
        ArrayUtils.reverse(intArray);

        System.out.println(Arrays.toString(intArray));
    }

    // 移除数组中的元素
    @Test
    public void testYC() {
        int[] intArray = {1, 2, 3, 4, 5};
        int[] removed = ArrayUtils.removeElement(intArray, 3); // create a new array

        System.out.println(Arrays.toString(removed));
    }

    // 将整数转换为字节数组
    @Test
    public void testBYTE() {
        byte[] bytes = ByteBuffer.allocate(4).putInt(8).array();

        for (byte t : bytes)
            System.out.format("0x%x ", t);
    }

}
