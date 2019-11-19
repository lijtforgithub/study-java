package com.ljt.study.jvm.load;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

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
        // TestLoader 静态语句块里输出了自己的类加载器
        Class<?> clazz = Class.forName(CLASS_NAME);
        System.out.println(clazz.getClassLoader().getParent());
    }

    @Test
    public void testMyClassLoader() throws ClassNotFoundException {
        // 自定义的类加载器的类加载器是 AppClassLoader
        System.out.println(MyClassLoader.class.getClassLoader());

        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz1 = loader.loadClass(CLASS_NAME);
        Class<?> clazz2 = loader.loadClass(CLASS_NAME);
        System.out.println(clazz1.getClassLoader());
        System.out.println(clazz1.getClassLoader().getParent());
        // 双亲委派 只加载一次
        System.out.println(clazz1 == clazz2);
    }

    @Test
    public void testMyClassLoaderAndClassForName() throws ClassNotFoundException {
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
        Class<?> clazz1 = loader.loadClass(CLASS_NAME);
        System.out.println("loadClass不会执行静态语句块");
        /**
         * Class.forName 默认使用AppClassLoader 默认类初始化。这里使用单个参数为报错。因为不在classpath
         * 指定类加载器，并且初始化。静态语句块有输出。
         */
        Class<?> clazz2 = Class.forName(CLASS_NAME, true, loader);;
        System.out.println("Class.forName会执行静态语句块");
        System.out.println(clazz1 == clazz2);
    }

    /**
     * 测试自定义类加载器重写 findClass 和 loadClass 的区别
     * 执行时要把加载的类文件放到classpath下，因为是为了测试双亲委派模型。
     * 类加载器和类本身一同确立其在Java虚拟机中的唯一性。
     */
    @Test
    public void testOverLoad() throws ClassNotFoundException {
        // AppClassLoader 加载
        Class<?> clazz1 = new MyClassLoader().loadClass(CLASS_NAME);
        System.out.println(clazz1.hashCode() + " = " + clazz1.getClassLoader());
        Class<?> clazz2 = new MyClassLoader().loadClass(CLASS_NAME);
        System.out.println(clazz2.hashCode() + " = " + clazz2.getClassLoader());
        /**
         * MyClassLoaderOverLoad
         * 如果用同一个加载器加载同一个类 会产生java.Lang.LinkageError异常
         */
        Class<?> clazz3 = new MyClassLoaderOverLoad().loadClass(CLASS_NAME);
        System.out.println(clazz3.hashCode() + " = " + clazz3.getClassLoader());
        Class<?> clazz4 = new MyClassLoaderOverLoad().loadClass(CLASS_NAME);
        System.out.println(clazz4.hashCode() + " = " + clazz3.getClassLoader());
    }

    @Test
    public void testSetParent() {
        MyClassLoader parent = new MyClassLoader();
        ClassLoader classLoader = new ClassLoader(parent) {};
        System.out.println(classLoader.getParent().getClass());
    }


    /**
     * 自定义加载器的测试类
     */
    private static final String CLASS_NAME = "com.ljt.study.jvm.TestLoader";
    /**
     * 自定义加载器的加载路径
     */
    private static final String LOAD_PATH = "D:/Workspace/IDEA/study/test/";


    /**
     * 自定义类加载器 重写findClass
     */
    private class MyClassLoader extends ClassLoader {

        public MyClassLoader() {
        }

        public MyClassLoader(ClassLoader parent) {
            super(parent);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] bytes = getClassData(name);
                if (Objects.isNull(bytes)) {
                    return super.findClass(name);
                }
                return super.defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(e.getMessage());
            }
        }
    }

    /**
     * 文件转换字节数组
     */
    private static byte[] getClassData(String name) throws IOException {
        File file = new File(LOAD_PATH + name.replace(".", "/").concat(".class"));
        if (!file.exists()) {
            return null;
        }
        FileInputStream input = new FileInputStream(file);
        byte[] bytes = new byte[input.available()];
        input.read(bytes);

        return bytes;
    }

    /**
     * 自定义类加载器 loadClass
     */
    private static class MyClassLoaderOverLoad extends ClassLoader {

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                byte[] bytes = getClassData(name);
                // 保证核心类库的加载
                if (Objects.isNull(bytes)) {
                    return super.loadClass(name);
                }
                return super.defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(e.getMessage());
            }
        }
    }

}
