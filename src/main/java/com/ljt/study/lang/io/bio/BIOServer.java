package com.ljt.study.lang.io.bio;

import java.io.IOException;
import java.io.InputStream;
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
class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEF_PORT, BACK_LOG);
        System.out.println("服务启动成功：" + server.getLocalPort());

        while (true) {
            try {
                // accept 阻塞
                Socket client = server.accept();
                System.out.printf("[%s] 进来一个客户端：%s %n", Thread.currentThread().getName(), client.getRemoteSocketAddress());

                StringBuilder msg = new StringBuilder();
                InputStream input = client.getInputStream();
                // read 方法阻塞
                int i = input.read();
                System.out.println("验证read方法阻塞:" + i);
                msg.append((char) i);

                byte[] buf = new byte[128];
                int len;
                /*
                 * 客户端的输出流必须调用 output.close() 才会有-1 不然不会跳出循环
                 * 用浏览器不会结束循环配合 BIOClient 调试使用
                 */
                while ((len = input.read(buf, 0, buf.length)) != -1) {
                    msg.append(new String(buf, 0, len));
                }

                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
