package com.ljt.study.lang.api;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author LiJingTang
 * @date 2020-01-02 17:34
 */
public class StackTraceTest {

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    public static String getFileName() {
        System.out.println(Thread.currentThread().getStackTrace().length);

        return Thread.currentThread().getStackTrace()[2].getFileName();
    }

    public static void main(String args[]) {
        System.out.println(Thread.currentThread().getStackTrace().length);
        System.out.println(Thread.currentThread().getStackTrace()[0]);
        System.out.println(Thread.currentThread().getStackTrace()[1]);
        System.out.println("FileName: " + getFileName());
        System.out.println("ClassName: " + getClassName());
        System.out.println("MethodName: " + getMethodName());
        System.out.println("LineNumber: " + getLineNumber());

        Exception e = new NullPointerException("XXOO");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        System.out.println(sw);
    }

}
