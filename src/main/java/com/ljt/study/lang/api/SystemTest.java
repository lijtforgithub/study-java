package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map.Entry;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:42
 */
public class SystemTest {

    @Test
    public void testMethod() {
        System.out.println("当前时间戳（毫秒）：" + System.currentTimeMillis());
        System.out.println("移除系统属性  user.dir ：" + System.clearProperty("user.dir"));
        System.out.println("哈希码值： " + System.identityHashCode(new Object()));
        System.console().flush();

        System.gc(); // Runtime.getRuntime().gc()
        System.exit(0); // 正常退出 等于 Runtime.getRuntime().exit(0)
        System.exit(1); // 终止了程序 非正常退出
    }

    /**
     * 当前系统环境
     */
    @Test
    public void testGetenv() {
        for (Entry<String, String> entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        System.out.println();
        System.out.println(System.getenv("JAVA_HOME"));
        System.out.println(System.getenv("CATALINA_HOME"));
    }

    /**
     * 当前系统属性
     */
    @Test
    public void testGetProperty() {
        for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        System.out.println();
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("webapp.root"));

        System.exit(0); // 终止了程序 非正常退出

        for (Enumeration<?> eum = System.getProperties().propertyNames(); eum.hasMoreElements();) {
            String propertyName = String.valueOf(eum.nextElement());
            System.out.println(propertyName + " - " + System.getProperty(propertyName));
        }
    }

    @Test
    public void testArraycopy() {
        String[] src = { "a", "b", "c" };
        String[] dest = { "A", "B", "C", "D", "E", "F" };

        System.out.println(Arrays.toString(src));
        System.arraycopy(src, 0, dest, 2, 2);
        System.out.println(Arrays.toString(dest));
    }

}
