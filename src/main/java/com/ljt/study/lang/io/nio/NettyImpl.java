package com.ljt.study.lang.io.nio;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ljt.study.lang.io.DemoUtils.*;

/**
 * 充分利用CPU资源 一个fd执行耗时 在一个线性里会阻塞后续的fd处理
 * 将N个fd分组 每组一个selector 将一个selector压到一个线程上
 * 最好的线程数量 cpu 或 cpu*2
 * 单看一个线程 有个selector 又一部分fd且他们是线性的
 * 多个线程 他们在自己的cpu上执行 代表会有多个selector并行 且线程内是线性的 最终是fd并行处理
 *
 * @author LiJingTang
 * @date 2021-09-06 19:42
 */
class NIOServerMultiSelector {

    public static void main(String[] args) {
        SelectorThreadGroup group = new SelectorThreadGroup(3);
        group.bind(DEF_PORT);
    }

}

class SelectorThreadGroup {

    private final SelectorThread[] worker;
    private final AtomicInteger xid = new AtomicInteger();

    SelectorThreadGroup(int threadNum) {
        if (threadNum < 1) {
            throw new IllegalArgumentException("不能小于1");
        }

        worker = new SelectorThread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            worker[i] = new SelectorThread(this);
            new Thread(worker[i]).start();
        }
    }

    @SneakyThrows
    void bind(int port) {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port), BACK_LOG);
        printStart(server.getLocalAddress());

        addQueue(server);
    }

    SelectorThread nextWorker() {
        if (worker.length == 1) {
            return worker[0];
        }
        // 轮询就会很尴尬，倾斜
        int index = xid.getAndIncrement() % (worker.length - 1);
        return worker[index + 1];
    }

    void addQueue(Channel channel) {
        SelectorThread selectorThread = channel instanceof ServerSocketChannel ? worker[0] : nextWorker();
        selectorThread.getChannelQueue().add(channel);
        selectorThread.getSelector().wakeup();
    }
}

@Getter
class SelectorThread implements Runnable {

    private final Selector selector;
    private final SelectorThreadGroup group;
    private final LinkedBlockingQueue<Channel> channelQueue = new LinkedBlockingQueue<>();

    @SneakyThrows
    SelectorThread(SelectorThreadGroup group) {
        selector = Selector.open();
        this.group = group;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {
                            acceptHandler((ServerSocketChannel) key.channel());
                        } else if (key.isReadable()) {
                            NIOServerSelector.readHandler(key);
                        }
                    }
                }

                if (!channelQueue.isEmpty()) {
                    registerChannel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    private void registerChannel() {
        Channel channel = channelQueue.take();
        if (channel instanceof ServerSocketChannel) {
            ServerSocketChannel server = (ServerSocketChannel) channel;
            server.register(selector, SelectionKey.OP_ACCEPT);
        } else if (channel instanceof SocketChannel) {
            SocketChannel client = (SocketChannel) channel;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            client.register(selector, SelectionKey.OP_READ, buffer);
        }
    }

    private void acceptHandler(ServerSocketChannel serverChannel) {
        try {
            SocketChannel client = serverChannel.accept();
            client.configureBlocking(false);
            printAccept(client.getRemoteAddress());
            this.group.addQueue(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
