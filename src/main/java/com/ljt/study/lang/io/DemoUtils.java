package com.ljt.study.lang.io;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
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
    public static final String LOCAL_HOST = "localhost";
    public static final int DEF_PORT = 8888;
    /**
     * 影响accept 队列的长度：如果设置过小 出现  Connection refused: connect 错误
     */
    public static final int BACK_LOG = 1024;

    public static String buildResponse(String content) {
        StringBuilder sBuilder = new StringBuilder();
        final String html = String.format("<h1>%s</h1>", StringUtils.defaultIfBlank(content, "Hello World"));
        sBuilder.append("HTTP/1.1 200 OK").append(HTTP_SEP)
                .append("connection: Close").append(HTTP_SEP)
                .append("content-type: text/html").append(HTTP_SEP)
                .append("content-length: ").append(html.length()).append(HTTP_SEP)
                .append(HTTP_SEP)
                .append(html);

        return sBuilder.toString();
    }

    public static BufferedWriter buildWriter(OutputStream output) {
        return new BufferedWriter(new OutputStreamWriter(output));
    }

    public static ThreadPoolExecutor buildExecutor() {
        return new ThreadPoolExecutor(100, 100, 0, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void handleRequest(Socket socket, Class<?> clazz) throws IOException {
        byte[] bytes = new byte[socket.getInputStream().available()];
        socket.getInputStream().read(bytes);

        String msg = new String(bytes).split("HTTP/1.1")[0];
        System.out.printf("[%s] 收到客户端：%s 的内容：%s%n", Thread.currentThread().getName(), socket.getRemoteSocketAddress(), msg);

        BufferedWriter writer = buildWriter(socket.getOutputStream());

        sleep();
        writer.write(buildResponse(clazz.getName()));
        writer.flush();
    }

    public static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String readChannel(SocketChannel channel, ByteBuffer buffer) throws IOException {
        StringBuilder msg = new StringBuilder();
        int len;

        while ((len = channel.read(buffer)) > 0) {
            byte[] bytes = new byte[len];
            buffer.flip();
            buffer.get(bytes);
            buffer.clear();
            msg.append(new String(bytes));
        }
        return msg.toString();
    }

    public static void writeChannel(SocketChannel channel, ByteBuffer buffer, String content) throws IOException {
        final byte[] bytes = content.getBytes();
        int offset = 0;

        while (offset < bytes.length) {
            int len = Math.min(buffer.capacity(), bytes.length - offset);
            buffer.put(bytes, offset, len);
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
            offset += len;
        }
    }

}
