package com.ljt.study.lang.io.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class NIOServerSelector {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        /*
         * select/poll/epoll  优先选择epoll  但是可以-D修正
         * 在epoll模型下 open => epoll_create -> fd3
         */
        Selector selector = Selector.open();
        /*
         * server约等于listen状态的 fd4
         * register:
         * select/poll：JVM里开辟一个空间 fd4 放进去
         * epoll：epoll_ctl(fd3,ADD,fd4,EPOLLIN
         */
        server.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            try {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("注册事件：" + keys.size());

                /*
                 * 调用多路复用器(select/poll/epoll(epoll_wait))
                 * select/poll 内核select（fd4）poll(fd4)
                 * epoll内核epoll_wait()
                 * 参数可以带时间：没有时间0(阻塞，有时间设置一个超时)
                 * selector.wakeup() 结果返回0
                 * 懒加载：在select()调用的时候触发了epoll_ctl的调用 意思是上面的register没有立即执行epoll_ctl
                 * 可以在register后面加个打印 查看系统调用来验证
                 */
                while (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    /*
                     * 管你啥多路复用器，你呀只能给我状态，我还得一个一个的去处理他们的R/W
                     */
                    while (it.hasNext()) {
                        SelectionKey key = it.next();

                        if (key.isAcceptable()) {
                            acceptHandler(key, true);
                        }else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isValid() && key.isWritable()) {
                        /*
                         * 写事件 只要send-queue是空的就一定会返回可以写的事件 就会调用写的逻辑
                         * 什么时候写 不是依赖send-queue是不是有空间
                         * 1. 准备好写什么
                         * 2. send-queue是否有空间
                         * 3. 所以读事件一开始就要注册 但是写事件依赖以上关系 什么时候用 什么时候写
                         * 4. 如果一开始就注册了写事件 就会一直调起
                         */
                            System.out.println("W");
                            SocketChannel client = (SocketChannel) key.channel();
                            client.write(ByteBuffer.wrap("Server W".getBytes()));
                            // 发送完了就取消写事件，否则下次还会进入写事件分支（因为只要还可写，就会进入）
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                        }

                        // 必须要remove
                        it.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void acceptHandler(SelectionKey key, boolean registryWrite) {
        try {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            /*
             * accept接受连接且返回新连接的fd
             * select/poll 因为他们内核没有空间，那么在JVM中保存和前边的fd4那个listen的一起
             * epoll通过epoll_ctl把新的客户端fd注册到内核空间
             */
            SocketChannel client = serverChannel.accept();
            client.configureBlocking(false);
            printAccept(client.getRemoteAddress());
            /*
             * select/poll：JVM里开辟一个数组 fd7 放进去
             * epoll：epoll_ctl(fd3,ADD,fd7,EPOLLIN
             */
            int opt = registryWrite ? SelectionKey.OP_READ | SelectionKey.OP_WRITE : SelectionKey.OP_READ;
            client.register(key.selector(), opt, ByteBuffer.allocateDirect(1024));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readHandler(SelectionKey key) {
        try {
            System.out.println("R");
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            StringBuilder msg = new StringBuilder();

            while (true) {
                int len = client.read(buffer);

                if (len > 0) {
                    msg.append(readBuffer(buffer));
                } else if (len == 0) {
                    break;
                } else if (len == -1) {
                    printClose(client.getRemoteAddress());
                    client.close();
                    key.cancel();
                    break;
                }
            }

            if (StringUtils.isNotBlank(msg)) {
                printRead(client.getRemoteAddress(), msg.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
