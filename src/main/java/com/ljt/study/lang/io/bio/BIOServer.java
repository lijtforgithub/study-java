package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 阻塞IO模型
 *
 * @author LiJingTang
 * @date 2021-08-24 16:44
 */
class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        printStart(server.getLocalSocketAddress());

        while (true) {
            try {
                // accept 阻塞
                Socket client = server.accept();
                printAccept(client.getRemoteSocketAddress());
                InputStream input = client.getInputStream();

                // read 方法阻塞
                int i = input.read();
                char c = (char) i;
                System.out.printf("read 方法阻塞：%d = %c %n", i, c);

                byte[] buf = new byte[1024];
                int len;
                while (true) {
                    // read 方法阻塞
                    len = input.read(buf, 0, buf.length);
                    System.out.println(len);

                    if (len > 0) {
                        String msg = new String(buf, 0, len);
                        printRead(client.getRemoteSocketAddress(), msg);
                    } else if (len == -1) {
                        // 客户端close
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
