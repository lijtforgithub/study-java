package com.ljt.study.lang.type.enums;

/**
 * @author LiJingTang
 * @date 2019-11-22 22:29
 */
public class WeekTest {

    public static void main(String[] args) {
        AbstractWeek sun = AbstractWeek.SUN;
        AbstractWeek mon = AbstractWeek.MON;
        System.out.println("sun: " + sun + " -> " + sun.nextDay());
        System.out.println("mon: " + mon + " -> " + mon.nextDay());

        WeekEnum fri = WeekEnum.FRI;
        System.out.println(fri);
        System.out.println(fri.name());
        System.out.println(fri.ordinal());
        System.out.println(WeekEnum.valueOf("SAT"));
        System.out.println(WeekEnum.values().length);
    }

    public enum WeekEnum {

        SUN(), MON, TUE(2), WED, THU, FRI, SAT; // 枚举成员要放在枚举类的最前面

        private WeekEnum() {
            System.out.println("First Constructor");
        } // 构造方不能是public的

        private WeekEnum(int i) {
            System.out.println("Second Constructor");
        } // 有参数的构造方法
    }

    /**
     * 模拟枚举类
     */
    private static abstract class AbstractWeek {

        private AbstractWeek() {}

        public static final AbstractWeek MON = new AbstractWeek() {
            @Override
            public AbstractWeek nextDay() {
                return SUN;
            }
        };
        public static final AbstractWeek SUN = new AbstractWeek() {
            @Override
            public AbstractWeek nextDay() {
                return MON;
            }
        };

        public abstract AbstractWeek nextDay();

        /*public WeekDay nextDay() {
            if (this == SUN) {
                return MON;
            } else if (this == MON) {
                return SUN;
            } else {
                return null;
            }
        }*/

        @Override
        public String toString() {
            return this == MON ? "MON" : "SUN";
        }
    }

}
