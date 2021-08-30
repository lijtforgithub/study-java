package com.ljt.study.lang.io.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 非阻塞IO 没有使用多路复用器
 *
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 非阻塞 server.setOption(StandardSocketOptions.TCP_NODELAY, true)
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        LinkedList<SocketChannel> clientList = new LinkedList<>();

        for (; ; ) {
            try {
                SocketChannel client = server.accept();

                if (Objects.isNull(client)) {
//                    System.out.println("非阻塞 nonblocking IO");
                } else {
                    // 如果这里设置阻塞 则read会阻塞 客户端调用 client.close() 才会读取到输入
                    client.configureBlocking(false);
                    printAccept(client.getRemoteAddress());
                    clientList.add(client);
                }

                // 缓存区 没有碎片 重复利用减少GC
                ByteBuffer buffer = ByteBuffer.allocate(8);
                Iterator<SocketChannel> it = clientList.iterator();

                while (it.hasNext()) {
                    SocketChannel channel = it.next();
                    StringBuilder msg = new StringBuilder();

                    while (true) {
                        // 不会阻塞
                        int len = channel.read(buffer);

                        if (len > 0) {
                            msg.append(readBuffer(buffer));
                        } else if (len == 0) {
                            break;
                        } else if (len == -1) {
                            printClose(channel.getRemoteAddress());
                            it.remove();
                            break;
                        }
                    }

                    if (StringUtils.isNotBlank(msg)) {
                        printRead(channel.getRemoteAddress(), msg.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
