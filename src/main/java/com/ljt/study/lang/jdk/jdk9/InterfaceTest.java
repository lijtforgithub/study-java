package com.ljt.study.lang.jdk.jdk9;

/**
 * @author LiJingTang
 * @date 2025-06-25 10:30
 */
public interface InterfaceTest {

    /**
     * 允许在接口中使用私有方法
     */
    private void privateMethod() {
        System.out.println("privateMethod");
    }

}
