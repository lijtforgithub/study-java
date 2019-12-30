package com.ljt.study.jvm.dataarea;

/**
 * 栈深度溢出
 * VM Args:	-Xss180k
 *
 * @author LiJingTang
 * @date 2019-12-30 11:24
 */
public class StackSOF {

    public static void main(String[] args) {
        StackSOF stack = new StackSOF();

        try {
            stack.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + stack.stackLength);
            throw e;
        }
    }

    private int stackLength = 1;

    private void stackLeak() {
        stackLength++;
        stackLeak();
    }

}
