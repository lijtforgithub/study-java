package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.ljt.study.lang.io.DemoUtils.DEF_PORT;
import static com.ljt.study.lang.io.DemoUtils.LOCAL_HOST;

/**
 * @author LiJingTang
 * @date 2021-08-25 17:08
 */
class BIOClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket client = new Socket(LOCAL_HOST, DEF_PORT);

        TimeUnit.SECONDS.sleep(5);
        System.out.printf("开始给服务器 %s 发送消息 %n", client.getRemoteSocketAddress());

        OutputStream output = client.getOutputStream();
        output.write("A".getBytes());
        output.flush();

        TimeUnit.SECONDS.sleep(5);
        output.write("Hello BIO".getBytes());
        output.flush();

        TimeUnit.SECONDS.sleep(5);
        output.write("Hello BIO GO ON".getBytes());
        output.flush();

        System.out.println("消息发送完毕");
        TimeUnit.SECONDS.sleep(10);
        client.close();
    }

}
