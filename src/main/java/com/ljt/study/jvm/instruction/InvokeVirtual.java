package com.ljt.study.jvm.instruction;

/**
 * @author LiJingTang
 * @date 2019-11-25 21:02
 */
public class InvokeVirtual {

    public static void main(String[] args) {
        new InvokeVirtual().m();
    }

    public void m() {
        System.out.println("invokevirtual指令");
    }

}
