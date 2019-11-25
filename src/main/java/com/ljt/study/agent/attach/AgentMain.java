package com.ljt.study.agent.attach;

import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 * 在应用程序启动后的某个时机处理
 *
 * @author LiJingTang
 * @date 2019-11-21 10:01
 */
public class AgentMain {

    private AgentMain() {
    }

    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        System.out.printf("%s => agentmain方法：agentArgs = %s \n", AgentMain.class.toString(), agentArgs);

        inst.addTransformer(new MethodTransformer(), true);
        Class<?>[] classes = inst.getAllLoadedClasses();

        for (Class<?> clazz : classes) {
            if (matchClass(clazz.getName())) {
                System.out.println("匹配的类：" + clazz.getName());
                inst.retransformClasses(clazz);
            }
        }
    }

    private static boolean matchClass(String className) {
        return className.equals("com.ljt.study.agent.AgentTest");
    }

    private static boolean matchMethod(String methodName) {
        return methodName.equals("sayHello");
    }


    /**
     * 在匹配的方法前后加上逻辑
     */
    private static class MethodTransformer implements ClassFileTransformer {

        private static final ClassPool CLASS_POLL = ClassPool.getDefault();

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            className = className.replace("/", ".");
            CtClass ctClass = null;

            try {
                ctClass = CLASS_POLL.getCtClass(className);
//                ctClass = CLASS_POLL.makeClass(new ByteArrayInputStream(classfileBuffer));

                if (!ctClass.isInterface()) {
                    for (CtBehavior method : ctClass.getDeclaredBehaviors()) {
                        if (AgentMain.matchMethod(method.getName())) {
                            System.out.println("匹配的方法：" + ctClass.getName() + "." + method.getName());
                            method.insertBefore(beforeSrc(method.getName()));
                            method.insertAfter(afterSrc(method.getName()));
                        }
                    }

                    return ctClass.toBytecode();
                }
            } catch (Exception e) {
                throw new IllegalClassFormatException(e.getMessage());
            } finally {
                if (Objects.nonNull(ctClass)) {
                    ctClass.detach();
                }
            }

            return null;
        }

        private static String beforeSrc(String methodName) {
            return "System.out.println(\"我是方法【" + methodName + "】执行之前的逻辑\");";
        }

        private static String afterSrc(String methodName) {
            return "System.out.println(\"我是方法【" + methodName + "】执行之后的逻辑\");";
        }
    }

}
