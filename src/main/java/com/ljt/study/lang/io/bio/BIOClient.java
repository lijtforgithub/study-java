package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static com.ljt.study.lang.io.DemoUtils.DEF_PORT;
import static com.ljt.study.lang.io.DemoUtils.LOCAL_HOST;

/**
 * @author LiJingTang
 * @date 2021-08-25 17:08
 */
class BIOClient {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket(LOCAL_HOST, DEF_PORT);

        OutputStream output = client.getOutputStream();
        output.write("Hello BIO".getBytes());
        output.close();
    }

}
