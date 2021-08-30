package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 阻塞IO模型：单线程
 * QPS = 2
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class HttpServerBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        printStart(server.getLocalSocketAddress());

        while (true) {
            try {
                Socket client = server.accept();
                printAccept(client.getRemoteSocketAddress());

                handleRequest(client, HttpServerBIO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
