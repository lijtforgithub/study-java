package com.ljt.study.jvm.loading;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义类加载器
 *
 * @author LiJingTang
 * @date 2019-11-12 22:57
 */
public class CustomClassLoaderTest {
    
    @Test
    public void testLoadClass() throws ClassNotFoundException {
        String name = this.getClass().getName();
        Class<?> clazz = CustomClassLoaderTest.class.getClassLoader().loadClass(name);
        System.out.println(this.getClass() == clazz);
        System.out.println(clazz.getName());
    }

    /**
     * 把自定义的类的class文件放到JAVA_HOME/jre/classes/，会被Bootstrap ClassLoader加载。（必须是class文件）
     * 把自定义的类的class文件导出成jar放到JAVA_HOME/jre/lib/ext/，会被ExtClassLoader加载。（必须是jar文件）
     */
    @Test
    public void testMyClassInLoadPath() throws ClassNotFoundException {
        String className = "com.ljt.study.jvm.TestLoader";
        // TestLoader 静态语句块里输出了自己的类加载器
        Class<?> clazz = Class.forName(className);
        System.out.println(clazz.getClassLoader().getParent());
    }

    @Test
    public void testMyClassLoader() throws ClassNotFoundException {
        // 自定义的类加载器的类加载器是 AppClassLoader
        System.out.println(MyClassLoader.class.getClassLoader());
//        MyClassLoader loader = new MyClassLoader();
        /**
         * 如果设置parent为null
         *
         * if (parent != null) {
         *      c = parent.loadClass(name, false);
         * } else {
         *      c = findBootstrapClassOrNull(name);
         * }
         */
        MyClassLoader loader = new MyClassLoader(null);
        String className = "com.ljt.study.jvm.TestLoader";
        Class<?> clazz1 = loader.loadClass(className);
        Class<?> clazz2 = loader.loadClass(className);
        System.out.println(clazz1.getClassLoader());
        System.out.println(clazz1.getClassLoader().getParent());
        // 双亲委派 只加载一次
        System.out.println(clazz1 == clazz2);
    }
}

/**
 * 自定义类加载器
 */
class MyClassLoader extends ClassLoader {

    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * 自定义加载器的加载路径
     */
    private static final String LOAD_PATH = "D:/Workspace/IDEA/study/test/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            FileInputStream input = new FileInputStream(LOAD_PATH + name.replaceAll("\\.", "/").concat(".class"));
            byte[] bytes = new byte[input.available()];
            input.read(bytes);

            return super.defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }
}
