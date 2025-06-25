package com.ljt.study.lang.jdk.jdk12;

import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:40
 */
public class InstanceofTest {

    @Test
    void instanceOf() {
        Object obj = "我是字符串";
        if(obj instanceof String str){
            System.out.println(str);
        }
    }

}
