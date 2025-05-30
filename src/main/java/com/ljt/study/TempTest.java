package com.ljt.study;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LiJingTang
 * @date 2021-03-08 14:28
 */
public class TempTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://osstest.huafagroup.com/redex-dev/user/2023/08/14/1435ad6869aa44b5bb7bab942c3b2c21/icon_file@2x.png");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        System.out.println(urlConnection.getResponseCode());
//        InputStream inputStream = url.openStream();
//        System.out.println(inputStream.available());

        Throwable t = new IllegalArgumentException("xxx");
        Exception e = new Exception("XXOO", t);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        System.out.println(sw.toString());
//        System.out.println(e.getCause());


    }

}
