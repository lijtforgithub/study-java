package com.ljt.study.lang.io.nio;

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
                 */
                while (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    /*
                     * 管你啥多路复用器，你呀只能给我状态，我还得一个一个的去处理他们的R/W
                     */
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {
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
                            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocateDirect(1024));
                        } else if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            String msg = readBuffer(buffer);
                            printRead(client.getRemoteAddress(), msg);
                        } else if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            if (buffer.hasRemaining()) {
                                client.write(buffer);
                            } else {
                                // 发送完了就取消写事件，否则下次还会进入写事件分支（因为只要还可写，就会进入）
                                key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                            }
                        } else if (!key.isValid()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            key.cancel();
                            System.out.println("关闭：" + client.getRemoteAddress());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
