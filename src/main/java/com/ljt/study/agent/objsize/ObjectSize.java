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

    private ObjectSize() {
    }

    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("JVM 载入 agent：" + ObjectSize.class.toString());
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
