package com.ljt.study.lang.jdk10;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2025-06-25 11:18
 */
public class VarTest {

    /**
     * var 关键字只能用于带有构造器的局部变量和 for 循环中。
     */
    @Test
    void var() {
        var id = 0;
        var list = List.of(1, 2, 3);
        var map = new HashMap<String, String>();
        var numbers = List.of("a", "b", "c");
        for (var n : list)
            System.out.print(n+ " ");

//        var count=null; //❌编译不通过，不能声明为 null
//        var r = () -> Math.random();//❌编译不通过,不能声明为 Lambda表达式
//        var array = {1,2,3};//❌编译不通过,不能声明数组
    }

}
