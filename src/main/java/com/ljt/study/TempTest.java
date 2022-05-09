package com.ljt.study;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    public static void main(String[] args) {
        Throwable t = new IllegalArgumentException("xxx");
        Exception e = new Exception("XXOO", t);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        System.out.println(sw.toString());
//        System.out.println(e.getCause());
    }

}
