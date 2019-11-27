package com.ljt.study.lang.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author LiJingTang
 * @date 2019-11-27 21:10
 */
public class CollectionTest {

    /**
     * 通过Arrays.copyOf()方法产生的数组是一个浅拷贝。同时数组的clone()方法也是，集合的clone()方法也是。
     */
    @Test
    public void test() {
        ArrayList<User> list1 = new ArrayList<>(2);
        list1.add(new User(1, "LiJingTang_1", 24));
        System.out.println("list1：" + list1.get(0).getName());

        List<User> list2 = new ArrayList<>(list1); // Arrays.copyOf
        list2.get(0).setName("LiJingTang_2");
        System.out.println("list2：" + list2.get(0).getName());
        System.out.println("list1：" + list1.get(0).getName());

        List<User> list3 = (List<User>) list1.clone();
        list3.get(0).setName("LiJingTang_3");
        System.out.println("list3：" + list3.get(0).getName());
        System.out.println("list1：" + list1.get(0).getName());
    }

    @Test
    public void testMap() {
        Map<Integer, User> map = new HashMap<>(2);
        map.put(1, new User(1, "LiJingTang", 24));
        map.put(2, new User(2, "LiJingTang", 25));

        Collection<User> collection = (Collection<User>) map.values();
        System.out.println(collection);
        List<User> list = new ArrayList<>(map.values());
        System.out.println(list);
    }

    @Test
    public void testHashSet() {
        Set<User> set = new HashSet<>();
        set.add(new User(1, "LiJingTang", 24));
        set.add(new User(2, "LiJingTang", 25));
        User user = new User(2, "LiJingTang", 25);
        set.add(user);
        System.out.println(set.size()); // 一样的hashCode只有一个实例对象

        user.setName("ZHENHUAN");
        set.remove(user); // 修改了参与计算hashCode值字段 单独删除不了这个对象 内存溢出
        System.out.println(set.size());
    }

    @Test
    public void testCompareToAndEquals() {
        List<User> list = new ArrayList<>(2);
        list.add(new User(1, "LiJingTang", 24));
        list.add(new User(2, "LiJingTang", 25));

        User user = new User(2, "LiJingTang", 25);
        System.out.println(list.indexOf(user)); // indexOf是基于equals来实现的只要equals返回TRUE就认为已经找到了相同的元素
        System.out.println(Collections.binarySearch(list, user)); // binarySearch是基于compareTo方法的，当compareTo返回0时就认为已经找到了该元素
    }


    private static class User implements Comparable<User> {

        private int id;
        private String name;
        private int age;

        public User(int id, String name, int age) {
            super();
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public int compareTo(User o) {
            return this.age - o.age;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + age;
            result = prime * result + id;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            User other = (User) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    }

}
