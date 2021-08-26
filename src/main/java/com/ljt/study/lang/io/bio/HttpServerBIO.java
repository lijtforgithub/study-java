package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 阻塞IO模型
 *
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class HttpServerBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        System.out.println("服务启动成功：" + server.getLocalPort());

        while (true) {
            try {
                Socket client = server.accept();
                System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), client.getRemoteSocketAddress());
                handleRequest(client, HttpServerBIO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
