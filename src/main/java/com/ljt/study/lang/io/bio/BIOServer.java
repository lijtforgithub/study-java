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

    // server socket listen property
    private static final int RECEIVE_BUFFER = 10;
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;
    private static final int BACK_LOG = 2;
    // client socket listen property on server endpoint
    private static final boolean CLI_KEEPALIVE = false;
    private static final boolean CLI_OOB = false;
    private static final int CLI_REC_BUF = 20;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 20;
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;
    private static final int CLI_TIMEOUT = 0;
    private static final boolean CLI_NO_DELAY = false;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        server.setReceiveBufferSize(RECEIVE_BUFFER);
        server.setReuseAddress(REUSE_ADDR);
        server.setSoTimeout(SO_TIMEOUT);
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
