package com.ljt.study.lang.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-11-27 20:43
 */
public class SubListTest {

    /**
     * subList返回仅仅只是一个视图。
     * SubLsit是ArrayList的内部类，它与ArrayList一样，都是继承AbstractList和实现RandomAccess接口。
     */
    @Test
    public void testEquals() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

        List<Integer> list2 = new ArrayList<>(list1); // 通过构造函数新建一个包含list1的列表 list2
        List<Integer> list3 = list1.subList(0, list1.size()); // 通过subList生成一个与list1一样的列表 list3

        list3.add(3); // 修改list3

        System.out.println("list1 == list2：" + list1.equals(list2));
        System.out.println("list1 == list3：" + list1.equals(list3));
        System.out.println(list1);
    }

    /**
     * 同时我们知道modCount 在new的过程中 "继承"了原列表modCount，只有在修改该列表（子列表）时才会修改该值（先表现在原列表后作用于子列表）。
     * 而在该实例中我们是操作原列表，原列表的modCount当然不会反应在子列表的modCount上啦，所以才会抛出该异常。
     * 对于子列表视图，它是动态生成的，生成之后就不要操作原列表了，否则必然都导致视图的不稳定而抛出异常。
     * 最好的办法就是将原列表设置为只读状态，要操作就操作子列表
     */
    @Test
    public void testUnmodifiable() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

        List<Integer> list2 = list1.subList(0, list1.size());
        list1.add(3);
        list1 = Collections.unmodifiableList(list1);
        list1.add(3);
    }

    /**
     * 使用subList处理局部列表
     */
    @Test
    public void testUse() {
        List<Integer> list = new ArrayList<>(10);

        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        System.out.println(list);
        list.subList(3, 8).clear();
        System.out.println(list);
    }

}
