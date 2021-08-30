package com.ljt.study.lang.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 阻塞IO模型
 *
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class BIOServerBuffer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        printStart(server.getLocalSocketAddress());

        while (true) {
            try {
                Socket client = server.accept();
                printAccept(client.getRemoteSocketAddress());

                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                while (true) {
                    // readLine 会阻塞 \r或\n才返回
                    String msg = input.readLine();
                    printRead(client.getRemoteSocketAddress(), msg);

                    if (Objects.isNull(msg)) {
                        printClose(client.getRemoteSocketAddress());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
