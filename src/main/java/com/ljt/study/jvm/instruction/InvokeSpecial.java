package com.ljt.study.jvm.instruction;

/**
 * @author LiJingTang
 * @date 2019-11-25 21:15
 */
public class InvokeSpecial {

    public static void main(String[] args) {
        InvokeSpecial special = new InvokeSpecial();
        special.m();
        special.n();
    }

    public final void m() {
    }

    private void n() {
        System.out.println("invokespecial指令");
    }

}
