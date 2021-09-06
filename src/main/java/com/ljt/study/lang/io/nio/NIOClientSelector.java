package com.ljt.study.lang.io.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.ljt.study.lang.io.DemoUtils.DEF_PORT;

/**
 * @author LiJingTang
 * @date 2021-09-06 11:56
 */
class NIOClientSelector {

    @SneakyThrows
    public static void main(String[] args) {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(DEF_PORT));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();

                if (key.isConnectable()) {
                    // 连接建立或者连接建立不成功
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 完成连接建立
                    if (channel.finishConnect()) {
                        System.out.println("finishConnect");
                    }
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.clear();
                    channel.read(buffer);
                    // buffer Handler
                }
                it.remove();
            }
        }
    }

}
