package com.ljt.study.dp.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.IntStream;

/**
 * 动态代理
 *
 * @author LiJingTang
 * @date 2019-12-14 14:48
 */
public class DynamicProxy {

    public static void main(String[] args) throws Exception {
        Tank tank = new Tank();
        InvocationHandler handler = new TimeInvocationHandler(tank);
        Movable proxy = (Movable) Proxy.newProxyInstance(Movable.class, handler);
        proxy.move();
    }

    private static class Proxy {

        private static String nTab(int n) {
            String tab = "    ";
            StringBuilder sBuilder = new StringBuilder();
            IntStream.range(0, n).forEach(i -> sBuilder.append(tab));
            return sBuilder.toString();
        }

        // 运行时动态编译 JDK6 Compiler API、CGLIB、ASM
        public static Object newProxyInstance(Class<?> inter, InvocationHandler handler) throws Exception {
            String sep = System.lineSeparator();
            String className = "$LiJTProxy";
            String methodStr = "";
            Method[] methods = inter.getMethods();

            for (Method method : methods) {
                methodStr += sep +
                nTab(1) + "@Override" + sep +
                nTab(1) + "public void " + method.getName() + "() {" + sep +
                nTab(2) + "try {" + sep +
                nTab(3) + "Method m = " + inter.getName() + ".class.getMethod(\"" + method.getName() + "\");" + sep +
                nTab(3) + "h.invoke(this, m);" + sep +
                nTab(2) + "} catch (Exception e) {" + sep +
                nTab(3) + "e.printStackTrace();" + sep +
                nTab(2) + "}" + sep +
                nTab(1) + "}";
            }

            String src =
                "import java.lang.reflect.Method;" + sep +
                "import " + InvocationHandler.class.getPackage().getName() + "." +
                    InvocationHandler.class.getSimpleName() + ";" + sep + sep +
                "public class " + className + " implements " + inter.getName() + " {" + sep +
                nTab(1) + "private InvocationHandler h;" + sep +
                nTab(1) + "public " + className + "(InvocationHandler h) {" + sep +
                nTab(2) + "super();" + sep +
                nTab(2) + "this.h = h;" + sep +
                nTab(1) + "}" + sep +
                nTab(1) + methodStr + sep +
                "}";


            String path = "D:/Workspace/IDEA/study/test/proxy/";
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File file = new File(parent, className + ".java");
            FileWriter writer = new FileWriter(file);
            writer.write(src);
            writer.flush();
            writer.close();

            /**编译源码*/
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, units);
            task.call();
            fileManager.close();

            /**加载到内存生成对象*/
            URL[] urls = new URL[]{new URL("file:/" + path)};
            URLClassLoader loader = new URLClassLoader(urls);
            Class<?> clazz = loader.loadClass(className);
            System.out.println(clazz);

            Constructor<?> constructor = clazz.getConstructor(InvocationHandler.class);

            return constructor.newInstance(handler);
        }
    }

    private static class TimeInvocationHandler implements InvocationHandler {

        private Object target; // 被代理的对象

        public TimeInvocationHandler(Object target) {
            super();
            this.target = target;
        }

        @Override
        public void invoke(Object obj, Method method) {
            long startTime = System.currentTimeMillis();
            try {
                method.invoke(this.target);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("costTime:" + (endTime - startTime));
        }
    }

}
