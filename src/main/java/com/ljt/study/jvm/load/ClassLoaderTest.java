package com.ljt.study.jvm.load;

import org.junit.jupiter.api.Test;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.URL;
import java.util.Objects;

/**
 * 三个类加载器
 *
 * @author LiJingTang
 * @date 2019-11-10 20:51
 */
public class ClassLoaderTest {

    @Test
    public void printClassLoader() {
        System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
        System.out.println(DNSNameService.class.getClassLoader().getClass().getName());
        System.out.println(Object.class.getClassLoader());

        System.out.println("sun.boot.class.path - " + System.getProperty("sun.boot.class.path"));
        System.out.println("java.ext.dirs - " + System.getProperty("java.ext.dirs"));
        System.out.println("java.class.path - " + System.getProperty("java.class.path"));
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
