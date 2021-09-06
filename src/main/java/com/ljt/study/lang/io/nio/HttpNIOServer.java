package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 非阻塞IO模型（没有使用多路复用器） + 单线程
 * QPS = 2
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class HttpNIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 非阻塞
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        for (; ; ) {
            try {
                SocketChannel client = server.accept();

                if (Objects.nonNull(client)) {
                    client.configureBlocking(false);
                    printAccept(client.getRemoteAddress());

                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    handleRequest(client, buffer, HttpNIOServer.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
