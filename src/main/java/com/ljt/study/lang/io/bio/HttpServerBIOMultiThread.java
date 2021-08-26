package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class HttpServerBIOMultiThread {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        System.out.println("服务启动成功：" + server.getLocalPort());
        ThreadPoolExecutor executor = buildExecutor();

        while (true) {
            try {
                Socket client = server.accept();
                System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), client.getRemoteSocketAddress());

                executor.submit(new SocketHandler(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SocketHandler implements Runnable {

        private Socket socket;

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                handleRequest(socket, HttpServerBIOMultiThread.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
