package com.ljt.study.lang.io.socket;

import com.ljt.study.juc.ThreadUtils;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author LiJingTang
 * @date 2019-11-28 10:18
 */
class UDP {

    public static void main(String[] args) {
        int port = 9999;
        Server server = new Server(port);

        new Thread(server).start();

        for (int i = 0; i < 1; i++) {
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
                byte[] buf = new byte[1024 * 1024];
                DatagramSocket server = new DatagramSocket(port);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                while (true) {
                    server.receive(packet);
                    ByteArrayInputStream input = new ByteArrayInputStream(buf);
                    System.out.println(new String(buf));
                    DataInputStream dataInput = new DataInputStream(input);

                    System.out.println("Server：" + "FROM ->" + packet.getAddress() + ":" + packet.getPort());
                    System.out.println("Server：" + dataInput.readUTF());
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
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                DataOutputStream dataOutput = new DataOutputStream(byteOutput);
                dataOutput.writeBytes("Client " + name);
                byte[] buf = byteOutput.toByteArray();

                DatagramSocket client = new DatagramSocket(6789);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, new InetSocketAddress(host, port));
                client.send(packet);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
