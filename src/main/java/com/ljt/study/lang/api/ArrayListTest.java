package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:51
 */
public class ArrayListTest {

    @Test
    public void testMethod() {
        ArrayList<String> arrayList = new ArrayList<>(2);
        arrayList.add("Hello");
        arrayList.add("World");
        System.out.println(arrayList);
        System.out.println(arrayList.toArray().getClass().getSimpleName());

        String[] array = {"Hello", "World"};
        List<String> list = Arrays.asList(array);
        Object[] elementData = list.toArray();
        System.out.println(Arrays.toString(elementData));
        System.out.println(elementData.getClass().getSimpleName());
        System.out.println(elementData[0].getClass().getSimpleName());

        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, elementData.length, Object[].class);
        System.out.println(elementData.getClass().getSimpleName());
    }

    @Test
    public void testToArray() {
        ArrayList<String> arrayList = new ArrayList<>(2);
        arrayList.add("Hello");
        arrayList.add("World");

        String[] array = new String[5];
        System.out.println(Arrays.toString(array));
        System.arraycopy(arrayList.toArray(), 0, array, 0, arrayList.size());
        System.out.println(Arrays.toString(array));
        String[] toArray = arrayList.toArray(array);

        System.out.println(array == toArray);
        System.out.println(Arrays.toString(toArray));
    }

}
