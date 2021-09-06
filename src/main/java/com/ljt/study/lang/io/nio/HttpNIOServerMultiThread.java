package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 非阻塞IO模型（没有使用多路复用器） + 多线程
 *
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class HttpNIOServerMultiThread {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 非阻塞
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        ThreadPoolExecutor executor = buildExecutor();

        for (; ; ) {
            try {
                SocketChannel client = server.accept();

                if (Objects.nonNull(client)) {
                    client.configureBlocking(false);
                    printAccept(client.getRemoteAddress());
                    executor.submit(new SocketHandler(client));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static class SocketHandler implements Runnable {

        private SocketChannel socketChannel;

        public SocketHandler(SocketChannel socketChannel) {
            super();
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                handleRequest(socketChannel, buffer, HttpNIOServerMultiThread.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
