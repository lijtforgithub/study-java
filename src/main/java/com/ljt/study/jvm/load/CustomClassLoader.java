package com.ljt.study.jvm.load;

/**
 * 自定义类加载器
 *
 * @author LiJingTang
 * @date 2019-11-12 22:57
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
        super.defineClass()
    }
}
