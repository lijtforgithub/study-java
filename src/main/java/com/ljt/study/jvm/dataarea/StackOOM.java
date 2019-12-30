package com.ljt.study.jvm.dataarea;

/**
 * 栈OOM
 * VM Args:	-Xss2M
 *
 * @author LiJingTang
 * @date 2019-12-30 11:27
 */
public class StackOOM {

    private void dontStop() {
		while (true) {

		}
	}

    private void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(() -> dontStop());
			thread.start();
		}
	}
/*
    不可执行
	public static void main(String[] args) {
		StackOOM stack = new StackOOM();
		stack.stackLeakByThread();
	}
*/
}
