package com.ljt.study.lang.generic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-11-23 21:27
 */
public class GenericTest {

    @Test
    public void testGeneric() throws Exception {
        List<Integer> list1 = new ArrayList<>(2);
        List<String> list2 = new ArrayList<>(1);
        list1.add(1);
        list2.add(new Integer(1).toString());
        System.out.println(list1.getClass() == list2.getClass()); // 同一份字节码

        list1.getClass().getMethod("add", Object.class).invoke(list1, "Java Generic");
        System.out.println(list1.get(list1.size() - 1));

        List list3 = new ArrayList<>();
        List<String> list4 = list3;

        // 参数化类型不能继承
        // List<Object> list5 = new ArrayList<String>();
        // List<String> list6 = new ArrayList<Object>();
    }

    @Test
    public void testList() {
        List<String> list = new ArrayList<>(2);
        list.add("String");
        // list.add(new Integer(1));

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        System.out.println(list1 instanceof ArrayList);
        System.out.println(list2 instanceof ArrayList);

        // System.out.println(list1 instanceof ArrayList<String>);
    }

    @Test
    public void testGenericStack() {
        GenericClass<String> stack1 = new GenericClass<>();
        stack1.push("London");
        stack1.push("Paris");
        stack1.push("Berlin");
    }

    @Test
    public <T> void test() {
        // T obj = new T();
        // T[] objArray = new T[5];
        T[] objs = (T[]) new Object[]{"hello", "world", 1L};

        // 不允许泛型类型创建泛型数组
        // ArrayList<String>[] list = new ArrayList<String>[10];
        ArrayList<String>[] list = new ArrayList[10];
    }

    private <T extends Exception> void testGenericException() throws T {
        try {

        } catch (Exception e) {
            throw (T) e;
        }
    }

    // public class Test<E>{
    // public static void m(E o1){};
    //
    // public static E o1;
    //
    // static {
    // E o2;
    // }
    // }

}
