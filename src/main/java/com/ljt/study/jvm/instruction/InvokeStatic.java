package com.ljt.study.jvm.instruction;

/**
 * @author LiJingTang
 * @date 2019-11-25 20:56
 */
public class InvokeStatic {

    public static void main(String[] args) {
        m();
    }

    private static void m() {
        System.out.println("invokestatic指令");
    }

}
