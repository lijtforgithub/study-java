package com.ljt.study.agent.pre;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.*;

/**
 * 在main方法之前运行
 *
 * @author LiJingTang
 * @date 2019-11-21 08:46
 */
public class PreMain {

    private PreMain() {
    }

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中 并被同一个System ClassLoader装载
     *
     * @param agentArgs javaagent = 后的参数
     * @param inst 仪表
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("JVM 载入 agent：" + PreMain.class);
        System.out.println("两个参数的premain方法：agentArgs = " + agentArgs);

        inst.addTransformer(new MethodTimeTransformer());
    }

    /**
     * 如果不存在 premain(String agentArgs, Instrumentation inst)
     * 则会执行 premain(String agentArgs)
     */
    public static void premain(String agentArgs) {
        System.out.println("JVM 载入 agent：" + PreMain.class);
        System.out.println("一个参数的premain方法：agentArgs = " + agentArgs);
    }


    /**
     * 计算方法耗时
     */
    private static class MethodTimeTransformer implements ClassFileTransformer {

        private static final String SYS_LINE = System.lineSeparator();
        private static final String SYS_TIME = "System.currentTimeMillis();";
        private static final Map<String, Set<String>> METHOD_MAP = new HashMap<>();
        private static final ClassPool CLASS_POLL = ClassPool.getDefault();

        static {
            addMethod("com.ljt.study.agent.AgentTest", "sayHello");
            addMethod("com.ljt.study.agent.AgentTest", "sayBye");
        }

        private static void addMethod(String classFullName, String methodName) {
            Set<String> methodSet = METHOD_MAP.getOrDefault(classFullName, new HashSet<>());
            methodSet.add(methodName);
            METHOD_MAP.putIfAbsent(classFullName, methodSet);
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
            className = className.replace("/", ".");
            Set<String> methods = METHOD_MAP.get(className);
            if (Objects.isNull(methods)) {
                return null;
            }

            CtClass ctClass = null;

            try {
                ctClass = CLASS_POLL.getCtClass(className);

                for (String methodName : methods) {
                    CtMethod method = ctClass.getDeclaredMethod(methodName);
                    String newMethodName = methodName + "$old";
                    method.setName(newMethodName);

                    CtMethod newMethod = CtNewMethod.copy(method, methodName, ctClass, null);

                    String body = "{" + SYS_LINE +
                            "long startTime = " + SYS_TIME + SYS_LINE +
                            newMethodName + "($$);" + SYS_LINE + // 调用原有代码，类似于method(); ($$)表示所有的参数
                            "long endTime = " + SYS_TIME + SYS_LINE +
                            "System.out.println(\"方法 [" + methodName + "]cost:\" + (endTime - startTime) + \"ms.\");" +
                            SYS_LINE + "}";
                    newMethod.setBody(body);
                    ctClass.addMethod(newMethod);
                }

                return ctClass.toBytecode();
            } catch (Exception e) {
                throw new IllegalClassFormatException(e.getMessage());
            } finally {
                if (Objects.nonNull(ctClass)) {
                    ctClass.detach();
                }
            }
        }
    }

}
