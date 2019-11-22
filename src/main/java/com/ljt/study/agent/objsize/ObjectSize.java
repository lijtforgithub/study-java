package com.ljt.study.agent.objsize;

import java.lang.instrument.Instrumentation;
import java.util.Objects;

/**
 * 计算对象占用内存空间大小
 *
 * @author LiJingTang
 * @date 2019-11-20 20:34
 */
public class ObjectSize {

    private ObjectSize() {}

    private static Instrumentation instrumentation;

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中 并被同一个System ClassLoader装载
     *
     * @param agentArgs javaagent = 后的参数
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    /**
     * 返回对象占用字节
     *
     * @param obj 对象
     * @return 字节数
     */
    public static long sizeOf(Object obj) {
        Objects.requireNonNull(instrumentation, "Instrumentation 实例为空，请配合javaagent方式使用！");

        return instrumentation.getObjectSize(obj);
    }

}
