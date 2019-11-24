package com.ljt.study.lang.grammar;

import java.io.File;
import java.net.URISyntaxException;

/**
 *  Java中的getResourceAsStream有以下几种：
 * 	1. Class.getResourceAsStream(String path) ：
 * 	path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下获取。
 * 其实通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
 * 	2.Class.getClassLoader.getResourceAsStream(String path)：
 * 	默认则是从ClassPath根下获取，path不能以’/'开头， 最终是由ClassLoader获取资源。
 * 	3. ServletContext.getResourceAsStream(String path)：
 * 	默认从WebAPP根目录下取资源，Tomcat下path是否以’/'开头无所谓，当然这和具体的容器实现有关。
 * 	4. JSP下的application内置对象就是上面的ServletContext的一种实现。
 *
 * @author LiJingTang
 * @date 2019-11-21 15:54
 */
public class PathTest {

    public static void main(String[] args) throws URISyntaxException {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI());
        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println(PathTest.class.getClassLoader().getResource(""));
        System.out.println(PathTest.class.getClassLoader().getResource("/"));
        System.out.println(PathTest.class.getResource("/"));
        System.out.println(PathTest.class.getResource(""));

        System.out.println(new File(File.separator).getAbsolutePath());
        System.out.println("user.dir - " + System.getProperty("user.dir"));
    }

    static String getRootPath(Class<?> clazz) {
        String filePath = clazz.getResource(clazz.getSimpleName() + ".class").toString();
        int index = filePath.lastIndexOf("/");

        if (filePath.startsWith("jar")) // 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径
            filePath = filePath.substring(10, index);
        else if (filePath.startsWith("file")) // 当class文件在class文件中时，返回"file:/F:/ ..."样的路径
            filePath = filePath.substring(6, index);

        return filePath;
    }

}
