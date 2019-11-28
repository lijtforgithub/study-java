package com.ljt.study.lang.io.bio;

import com.ljt.study.juc.ThreadUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author LiJingTang
 * @date 2019-11-28 10:55
 */
public class BIOTest {

    public static void main(String[] args) {
        new Thread(new Server()).start();
        new Thread(new Client()).start();

        ThreadUtils.sleepSeconds(30);
        System.exit(0);
    }

    private static class Server implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(8080));
                while (true) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    SocketHandler handler = new SocketHandler(socketChannel);
                    new Thread(handler).start();
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
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            try {
                int num;
                while ((num = socketChannel.read(buffer)) > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[num];
                    buffer.get(bytes);

                    String msg = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("收到请求：" + msg);

                    ByteBuffer writeBuffer = ByteBuffer.wrap(("我已经收到你的请求，你的请求内容是：" + msg).getBytes());
                    socketChannel.write(writeBuffer);
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Client implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
                ByteBuffer buffer = ByteBuffer.wrap("我是一个兵".getBytes());
                socketChannel.write(buffer);

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int num;
                if ((num = socketChannel.read(readBuffer)) > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[num];
                    readBuffer.get(bytes);
                    String msg = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("返回值：" + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
