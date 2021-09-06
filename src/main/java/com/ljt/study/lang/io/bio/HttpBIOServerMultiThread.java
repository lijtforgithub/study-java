package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * BIO + 多线程（开辟线程过多 上下文切换 消耗资源）
 *
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class HttpBIOServerMultiThread {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        printStart(server.getLocalSocketAddress());
        ThreadPoolExecutor executor = buildExecutor();

        while (true) {
            try {
                Socket client = server.accept();
                printAccept(client.getRemoteSocketAddress());

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
                handleRequest(socket, HttpBIOServerMultiThread.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
