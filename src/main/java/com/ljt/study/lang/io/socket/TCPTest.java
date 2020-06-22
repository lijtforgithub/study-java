package com.ljt.study.lang.io.socket;

import com.ljt.study.juc.ThreadUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LiJingTang
 * @date 2019-11-28 10:09
 */
public class TCPTest {

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
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();
                    DataInputStream dis = new DataInputStream(in);
                    DataOutputStream dos = new DataOutputStream(out);

                    String msg = dis.readUTF();

                    if (StringUtils.isNotBlank(msg)) {
                        System.out.println("Server：" + msg);
                        System.out.println("Server：" + "FROM -> " + client.getInetAddress() + ":" + client.getPort());
                    }

                    dos.writeUTF("I am Server");
                    dis.close();
                    dos.close();
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
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                DataInputStream dis = new DataInputStream(in);
                DataOutputStream dos = new DataOutputStream(out);

                dos.writeUTF("I am Client " + name);
                String msg = dis.readUTF();

                if (StringUtils.isNotBlank(msg)) {
                    System.out.println("Client：" + msg);
                }

                dis.close();
                dos.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
