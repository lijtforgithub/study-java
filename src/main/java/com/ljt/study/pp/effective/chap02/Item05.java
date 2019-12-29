package com.ljt.study.pp.effective.chap02;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:41
 */
public class Item05 {

    @Test
    public void testAdapter() {
        Map<String, String> map = new HashMap<>(2);
        map.put("key_1", "value_1");
        map.put("key_2", "value_2");

        Set<String> set1 = map.keySet();
        Set<String> set2 = map.keySet();
        System.out.println(set1 == set2);
        set1.add("key_3");
        System.out.println(set2);
    }

    private static class Person {

        private final Date birthDate;
        private static final Date BOOM_START;
        private static final Date BOOM_END;

        public Person(Date birthDate) {
            this.birthDate = birthDate;
        }

        static {
            // 只在类初始化的时候加载一次 如果放到isBabyBoomer方法里 每次调用都会创建这些对象
            Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
            BOOM_START = gmtCal.getTime();
            gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
            BOOM_END = gmtCal.getTime();
        }

        public boolean isBabyBoomer() {
            return birthDate.compareTo(BOOM_START) >= 0 && birthDate.compareTo(BOOM_END) < 0;
        }
    }

}
