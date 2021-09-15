package com.ljt.study.lang.io;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2021-08-24 19:29
 */
public class DemoUtils {

    private DemoUtils() {
    }

    private static final String HTTP_SEP = "\r\n";
    private static final String HTTP_1_1 = "HTTP/1.1";
    public static final String LOCAL_HOST = "localhost";
    public static final int DEF_PORT = 8888;
    /**
     * 影响accept 队列的长度：如果设置过小 出现  Connection refused: connect 错误
     */
    public static final int BACK_LOG = 1024;

    private static String buildResponse(String content) {
        StringBuilder sBuilder = new StringBuilder();
        final String html = String.format("<h1>%s</h1>", StringUtils.defaultIfBlank(content, "Hello World"));
        sBuilder.append(HTTP_1_1).append(" 200 OK").append(HTTP_SEP)
                .append("connection: Close").append(HTTP_SEP)
                .append("content-type: text/html").append(HTTP_SEP)
                .append("content-length: ").append(html.length()).append(HTTP_SEP)
                .append(HTTP_SEP)
                .append(html);

        return sBuilder.toString();
    }

    public static void handleRequest(Socket socket, Class<?> clazz) throws IOException {
        byte[] bytes = new byte[socket.getInputStream().available()];
        socket.getInputStream().read(bytes);

        String msg = new String(bytes).split(HTTP_1_1)[0];
        printRead(socket.getRemoteSocketAddress(), msg);

        sleep();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter((socket.getOutputStream())));
        writer.write(buildResponse(clazz.getName()));
        writer.flush();
    }

    public static void handleRequest(SocketChannel channel, ByteBuffer buffer, Class<?> clazz) throws IOException {
        StringBuilder msg = new StringBuilder();
        boolean closed = false;

        while (true) {
            // 不会阻塞
            int len = channel.read(buffer);
            if (len > 0) {
                msg.append(readBuffer(buffer));
            } else if (len == 0) {
                break;
            } else if (len == -1) {
                printClose(channel.getRemoteAddress());
                closed = true;
                break;
            }
        }

        printRead(channel.getRemoteAddress(), msg.toString().split(HTTP_1_1)[0]);

        sleep();

        final byte[] bytes = buildResponse(clazz.getName()).getBytes();
        int offset = 0;
        while (offset < bytes.length) {
            int len = Math.min(buffer.capacity(), bytes.length - offset);
            buffer.put(bytes, offset, len);
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
            offset += len;
        }

        if (closed) {
            channel.close();
        }
    }

    public static String readBuffer(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        buffer.clear();
        return new String(bytes);
    }


    public static ThreadPoolExecutor buildExecutor() {
        return new ThreadPoolExecutor(100, 100, 0, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printAccept(SocketAddress address) {
        System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), address);
    }

    public static void printRead(SocketAddress address, String msg) {
        System.out.printf("[%s] 收到客户端：%s 的内容：%s %n", Thread.currentThread().getName(), address, msg);
    }

    public static void printClose(SocketAddress address) {
        System.out.printf("客户端：%s 断开 %n", address);
    }

    public static void printStart(SocketAddress address) {
        System.out.printf("[%s] 服务启动成功：%s %n", Thread.currentThread().getName(), address);
    }

}
