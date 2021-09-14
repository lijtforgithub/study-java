package com.ljt.study.lang.io.aio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2019-11-28 11:23
 */
class AIOServer {

    @SneakyThrows
    public static void main(String[] args) {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(DEF_PORT));
        printStart(server.getLocalAddress());
        Attachment attach = new Attachment();
        attach.setServer(server);

        server.accept(attach, new CompletionHandler<AsynchronousSocketChannel, Attachment>() {

            @Override
            public void completed(AsynchronousSocketChannel client, Attachment attachment) {
                try {
                    printAccept(client.getRemoteAddress());
                    attach.getServer().accept(attach, this);

                    Attachment attach = new Attachment();
                    attach.setServer(server);
                    attach.setClient(client);
                    attach.setReadMode(true);
                    attach.setBuffer(ByteBuffer.allocate(2048));

                    client.read(attach.getBuffer(), attach, new ChannelHandler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Attachment attachment) {
                System.out.println("accept failed");
            }
        });
    }

    private static class Attachment {

        private AsynchronousServerSocketChannel server;
        private AsynchronousSocketChannel client;
        private boolean isReadMode;
        private ByteBuffer buffer;

        public AsynchronousServerSocketChannel getServer() {
            return server;
        }

        public void setServer(AsynchronousServerSocketChannel server) {
            this.server = server;
        }

        public AsynchronousSocketChannel getClient() {
            return client;
        }

        public void setClient(AsynchronousSocketChannel client) {
            this.client = client;
        }

        public boolean isReadMode() {
            return isReadMode;
        }

        public void setReadMode(boolean isReadMode) {
            this.isReadMode = isReadMode;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }

        public void setBuffer(ByteBuffer buffer) {
            this.buffer = buffer;
        }
    }

    private static class ChannelHandler implements CompletionHandler<Integer, Attachment> {

        @Override
        public void completed(Integer result, Attachment attachment) {
            if (attachment.isReadMode()) {
                ByteBuffer buffer = attachment.getBuffer();
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                String msg = new String(buffer.array()).trim();
                System.out.println("收到来自客户端的数据：" + msg);

                buffer.clear();
                buffer.put("Response from server!".getBytes(StandardCharsets.UTF_8));
                attachment.setReadMode(false);
            } else {

            }
        }

        @Override
        public void failed(Throwable e, Attachment attachment) {
            System.out.println(e);
        }
    }

}
