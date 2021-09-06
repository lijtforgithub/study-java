package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import static com.ljt.study.lang.io.DemoUtils.DEF_PORT;

/**
 * @author LiJingTang
 * @date 2021-08-26 09:46
 */
class NIOClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel client = SocketChannel.open(new InetSocketAddress(DEF_PORT));
        // 这里设置客户端read会不会阻塞
        client.configureBlocking(false);

        client.read(ByteBuffer.allocate(10));

        TimeUnit.SECONDS.sleep(5);
        System.out.printf("开始给服务器 %s 发送消息 %n", client.getRemoteAddress());

        ByteBuffer buffer = ByteBuffer.wrap("Hello NIO".getBytes());
        client.write(buffer);

        TimeUnit.SECONDS.sleep(5);
        buffer.clear();
        buffer.put("NIO GO ON".getBytes());
        buffer.flip();
        client.write(buffer);

        System.out.println("消息发送完毕");
        TimeUnit.SECONDS.sleep(10);
        client.close();
    }

}
