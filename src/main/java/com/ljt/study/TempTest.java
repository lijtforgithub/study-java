package com.ljt.study;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    public static void main(String[] args) {
        Exception e = new NullPointerException("XXOO");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        System.out.println(sw.toString());
    }

}
