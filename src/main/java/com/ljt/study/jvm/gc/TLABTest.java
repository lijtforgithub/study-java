package com.ljt.study.jvm.gc;

/**
 * 逃逸分析 标量替换 线程专有对象分配
 * -XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB
 *
 * @author LiJingTang
 * @date 2020-01-05 11:12
 */
public class TLABTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            alloc(i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void alloc(int i) {
        new User(i, "Name-" + i);
    }

    private static class User {

        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
