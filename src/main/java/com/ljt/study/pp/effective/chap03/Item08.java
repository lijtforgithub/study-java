package com.ljt.study.pp.effective.chap03;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiJingTang
 * @date 2019-12-28 22:04
 */
public class Item08 {

    @Test
    /**对称性*/
    public void testSymmetry() {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "Polish";
        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println(list.contains(s)); // Sun的虚拟机实现返回false
    }

    @Test /**传递性*/
    public void testTransitivity() {
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        ColorPoint cp2 = new ColorPoint(1, 2, Color.BLUE);

        System.out.println(p.equals(cp));
        System.out.println(cp.equals(p));

        System.out.println(p.equals(cp2));
        System.out.println(cp.equals(cp2));

    }

    /**
     *  对称性：区分大小写的字符串
     */
    private static final class CaseInsensitiveString {

        private final String s;

        public CaseInsensitiveString(String s) {
            if (null == s) {
                throw new NullPointerException();
            }

            this.s = s;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CaseInsensitiveString) {
                return this.s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);
            }
            if (obj instanceof String) {
                return this.s.equalsIgnoreCase((String) obj);
            }

            return false;

            //		return obj instanceof CaseInsensitiveString && ((CaseInsensitiveString) obj).s.equalsIgnoreCase(this.s);
        }
    }


    /**
     * 传递性
     */
    private static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Point)) {
                return false;
            }

            Point p = (Point) obj;
            return p.x == this.x && p.y == this.y;
        }
    }

    private static class ColorPoint extends Point {

        private final Color color;

        public ColorPoint(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        @Override
        public boolean equals(Object obj) {
            /*if (!(obj instanceof ColorPoint)) {
                return false;
            }

            return super.equals(obj) && ((ColorPoint) obj).color == this.color;*/

            if (!(obj instanceof Point)) {
                return false;
            }

            if (!(obj instanceof ColorPoint)) {
                return obj.equals(this);
            }

            return super.equals(obj) && ((ColorPoint) obj).color == this.color;
        }
    }

    private static class ComponentColorPoint {

        private final Point point;
        private final Color color;

        public ComponentColorPoint(int x, int y, Color color) {
            if (null == color) {
                throw new NullPointerException();
            }

            this.point = new Point(x, y);
            this.color = color;
        }

        public Point asPoint() {
            return this.point;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ComponentColorPoint)) {
                return false;
            }

            ComponentColorPoint cp = (ComponentColorPoint) obj;

            return cp.point.equals(this.point) && cp.color.equals(this.color);
        }
    }

    private static class CounterPoint extends Point {

        private static final AtomicInteger counter = new AtomicInteger();

        public CounterPoint(int x, int y) {
            super(x, y);
            counter.incrementAndGet();
        }

        public int numberCreated() {
            return counter.get();
        }
    }


}
