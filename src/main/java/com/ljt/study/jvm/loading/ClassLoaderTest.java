package com.ljt.study.jvm.loading;

import org.junit.jupiter.api.Test;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.URL;
import java.util.Objects;

/**
 * 类加载器
 *
 * @author LiJingTang
 * @date 2019-11-10 20:51
 */
public class ClassLoaderTest {

    @Test
    public void printClassLoader() {
        /**
         * 三种类加载器
         */
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(DNSNameService.class.getClassLoader());
        System.out.println(String.class.getClassLoader());

        /**
         * 类加载器（AppClassLoader和ExtClassLoader）的加载器都是Bootstrap ClassLoader
         * 自定义的类加载器的类加载器是 AppClassLoader
         */
        System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getClassLoader());
        System.out.println(DNSNameService.class.getClassLoader().getClass().getClassLoader());
    }

    @Test
    public void printClassLoaderAndParent() {
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent());
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent().getParent());
    }

    /**
     * 打印ClassLoader的加载路径
     */
    @Test
    public void printClassLoaderPath() {
        String separator = System.lineSeparator();
        System.out.println("sun.boot.class.path" + separator + System.getProperty("sun.boot.class.path").replaceAll(";",
                separator) + separator);
        System.out.println("java.ext.dirs" + separator + System.getProperty("java.ext.dirs").replaceAll(";",
                separator) + separator);
        System.out.println("java.class.path" + separator + System.getProperty("java.class.path").replaceAll(";",
                separator) + separator);
    }

    /**
     * 打印ClassLoader的parent
     */
    @Test
    public void printParent() {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();

        while (loader != null) {
            ClassLoader parent = loader.getParent();
            System.out.println(loader.getClass().getName() + " 的 parent = "
                    + (Objects.nonNull(parent) ? parent.getClass().getName() : null));
            loader = loader.getParent();
        }

        System.out.println("默认 parent = " + ClassLoader.getSystemClassLoader().getClass().getName());
    }

    @Test
    public void printBootstrapClassPath() {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }

}
