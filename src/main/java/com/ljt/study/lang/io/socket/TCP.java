package com.ljt.study.lang.io.socket;

import com.ljt.study.juc.ThreadUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在io包中，提供了两个与平台无关的数据操作流：
 * 数据输出流（DataOutputStream）
 * 数据输入流 （DataInputStream）
 * 使用DataOutputStream写入的数据要使用DataInputStream读取进来
 *
 * @author LiJingTang
 * @date 2019-11-28 10:09
 */
class TCP {

    public static void main(String[] args) {
        int port = 8888;
        Server server = new Server(port);
        new Thread(server).start();

        for (int i = 0; i < 10; i++) {
            Client client = new Client("localhost", port, "No." + (i + 1));
            new Thread(client).start();
            ThreadUtils.sleepSeconds(1);
        }

        ThreadUtils.sleepSeconds(30);
        System.exit(0);
    }


    private static class Server implements Runnable {

        private int port;

        public Server(int port) {
            super();
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(port);

                while (true) {
                    Socket client = server.accept();
                    DataInputStream input = new DataInputStream(client.getInputStream());
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());

                    String msg = input.readUTF();
                    if (StringUtils.isNotBlank(msg)) {
                        System.out.println("Server：" + msg);
                        System.out.println("Server：" + "FROM -> " + client.getInetAddress() + ":" + client.getPort());
                    }
                    output.writeUTF("I am Server");

                    input.close();
                    output.close();
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Client implements Runnable {

        private String host;
        private int port;
        private String name;

        public Client(String host, int port, String name) {
            super();
            this.host = host;
            this.port = port;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Socket client = new Socket(host, port);
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());

                output.writeUTF("I am Client " + name);
                String msg = input.readUTF();
                if (StringUtils.isNotBlank(msg)) {
                    System.out.println("Client：" + msg);
                }

                input.close();
                output.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
