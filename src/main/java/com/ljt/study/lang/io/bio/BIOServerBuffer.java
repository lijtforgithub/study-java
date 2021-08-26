package com.ljt.study.lang.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static com.ljt.study.lang.io.DemoUtils.BACK_LOG;
import static com.ljt.study.lang.io.DemoUtils.DEF_PORT;

/**
 * 阻塞IO模型
 *
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class BIOServerBuffer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        System.out.println("服务启动成功：" + server.getLocalPort());

        while (true) {
            try {
                Socket client = server.accept();
                System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), client.getRemoteSocketAddress());

                StringBuilder msg = new StringBuilder();
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String str;
                // readLine 会阻塞
                while ((str = input.readLine()) != null) {
                    msg.append(str);
                }

                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
