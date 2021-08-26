package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class HttpServerNIO {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 非阻塞
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        System.out.println("服务启动成功：" + server.getLocalAddress());

        for (; ; ) {
            try {
                SocketChannel client = server.accept();

                if (Objects.isNull(client)) {
                    System.out.println("非阻塞 nonblocking IO");
                } else {
                    client.configureBlocking(false);
                    System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), client.getRemoteAddress());

                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    String msg = readChannel(client, buffer).split("HTTP/1.1")[0];
                    System.out.printf("[%s] 收到客户端：%s 的内容：%s%n", Thread.currentThread().getName(), client.getRemoteAddress(), msg);

                    sleep();
                    writeChannel(client, buffer, buildResponse(HttpServerNIO.class.getName()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
