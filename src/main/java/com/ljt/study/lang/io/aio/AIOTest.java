package com.ljt.study.lang.io.aio;

import com.ljt.study.juc.ThreadUtils;
import com.ljt.study.lang.io.bio.BIOTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @author LiJingTang
 * @date 2019-11-28 11:23
 */
public class AIOTest {

    public static void main(String[] args) {
        new Thread(new Server()).start();
        new Thread(new BIOTest.Client()).start();

        ThreadUtils.sleepSeconds(30);
        System.exit(0);
    }

    private static class Server implements Runnable {

        @Override
        public void run() {
            try {
                AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8080));
                Attachment attach = new Attachment();
                attach.setServer(serverSocketChannel);

                serverSocketChannel.accept(attach, new CompletionHandler<AsynchronousSocketChannel, Attachment>() {

                    @Override
                    public void completed(AsynchronousSocketChannel client, Attachment attachment) {
                        try {
                            SocketAddress clientAddr = client.getRemoteAddress();
                            System.out.println("收到新的链接：" + clientAddr);
                            attach.getServer().accept(attach, this);

                            Attachment newAttach = new Attachment();
                            newAttach.setServer(serverSocketChannel);
                            newAttach.setClient(client);
                            newAttach.setReadMode(true);
                            newAttach.setBuffer(ByteBuffer.allocate(2048));

                            client.read(newAttach.getBuffer(), newAttach, new ChannelHandler());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Attachment attachment) {
                        System.out.println("accept failed");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        public void failed(Throwable exc, Attachment attachment) {
            // TODO Auto-generated method stub
        }
    }

}
