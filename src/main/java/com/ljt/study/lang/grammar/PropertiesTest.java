package com.ljt.study.lang.grammar;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 加载资源文件
 *
 * @author LiJingTang
 * @date 2019-11-21 15:33
 */
public class PropertiesTest {

    @Test
    public void test() throws IOException {
        System.out.println(PropertiesTest.class.getResource("/"));
        System.out.println(PropertiesTest.class.getResource(""));
        // 必须以 / 开头 不然以当前在当前包路径查找
        InputStream in = PropertiesTest.class.getResourceAsStream("/lang/prop.properties");
        Properties prop = new Properties();
        prop.load(in);

        System.out.println(prop.keySet());
        System.out.print(prop.getProperty("name", "none") + "\t");
        System.out.println(prop.getProperty("age", "none"));
    }

    @Test
    public void testClassLoader() throws IOException {
        System.out.println(PropertiesTest.class.getClassLoader().getResource("/"));
        System.out.println(PropertiesTest.class.getClassLoader().getResource(""));
        // 不能以 / 开头
        InputStream in = PropertiesTest.class.getClassLoader().getResourceAsStream("lang/prop.properties");
        Properties prop = new Properties();
        prop.load(in);

        System.out.print(prop.getProperty("name") + "\t");
        System.out.println(prop.getProperty("age"));
    }

    @Test
    public void testResourceBundle() {
        ResourceBundle rb = ResourceBundle.getBundle("lang/prop");

        System.out.print(rb.getString("name") + "\t");
        System.out.println(rb.getString("age"));
    }

}
