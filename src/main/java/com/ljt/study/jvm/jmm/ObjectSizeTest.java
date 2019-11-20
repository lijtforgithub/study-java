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
    }

}
