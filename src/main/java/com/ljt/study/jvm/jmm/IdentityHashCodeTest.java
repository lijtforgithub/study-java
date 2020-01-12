package com.ljt.study.jvm.jmm;

/**
 * @author LiJingTang
 * @date 2020-01-12 18:16
 */
public class IdentityHashCodeTest {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());
        System.out.println(System.identityHashCode(o));

        OverrideHashCode t = new OverrideHashCode();
        System.out.println(t.hashCode());
        System.out.println(t.superHashCode());
        System.out.println(System.identityHashCode(t));

        String s1 = new String("Hello");
        String s2 = new String("Hello");
        System.out.println(s1.hashCode() == s2.hashCode());
        System.out.println(System.identityHashCode(s1) == System.identityHashCode(s2));
    }

    private static final class OverrideHashCode {

        @Override
        public int hashCode() {
            return 1;
        }

        public int superHashCode() {
            return super.hashCode();
        }
    }

}
