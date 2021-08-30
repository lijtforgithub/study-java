package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class HttpServerNIOSelector {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 非阻塞
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        Selector selector = Selector.open();
        // 注册accept事件
        server.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            try {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("注册事件：" + keys.size());

                while (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                            SocketChannel client = serverChannel.accept();
                            client.configureBlocking(false);
                            printAccept(client.getRemoteAddress());

                            client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocateDirect(1024));
                        } else if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            handleRequest(client, buffer, HttpServerNIOSelector.class);
                        } else {
                            System.out.println("事件：" + key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
