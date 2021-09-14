package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * @author LiJingTang
 * @date 2021-08-24 16:46
 */
class NIOServerSelectorMultiThread {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(DEF_PORT), BACK_LOG);
        printStart(server.getLocalAddress());

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            try {
                while (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {
                            NIOServerSelector.acceptHandler(key, false);
                        } else if (key.isReadable()) {
                            /*
                             * 多线程读取的时候 有可能还没读完 主线程再次循环的时候又拿到读事件
                             * epoll_ctl(fd, del)
                             * cancel 之后后面的消息也接收不到了 再注册也不行
                             */
                            key.cancel();
                            executorService.submit(() -> NIOServerSelector.readHandler(key));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
