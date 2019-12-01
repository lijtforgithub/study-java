package com.ljt.study.jvm.jmm;

import com.ljt.study.agent.objsize.ObjectSize;

/**
 * 测试对象大小
 * VM参数：-javaagent:D:/Workspace/IDEA/study/target/artifacts/agent/agent-ObjectSize.jar
 *
 * @author LiJingTang
 * @date 2019-11-20 21:17
 */
public class ObjectSizeTest {

    public static void main(String[] args) {
        System.out.println(ObjectSize.sizeOf(new Object()));
        System.out.println(ObjectSize.sizeOf(new int[]{}));
        System.out.println(ObjectSize.sizeOf(new Obj()));
    }

    private static class Obj {
        // markword            4
        // ClassPointer        4
        // 数组长度             4
        private byte b;     // 1
        private int i;      // 4
        private String s;   // 4
        private Object o;   // 4
    }

}
