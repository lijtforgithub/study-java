package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

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


}

class SelectorTaskGroup {

}

class SelectorTask implements Runnable {

    private Selector selector;

    public SelectorTask() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Set<SelectionKey> keys = selector.keys();
                System.out.printf("%s | before select() | %d %n", Thread.currentThread().getName(), keys.size());
                int num = selector.select();
                System.out.printf("%s | after select() | %d %n", Thread.currentThread().getName(), keys.size());

                if (num > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();

                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (key.isAcceptable()) {

                        }
                        if (key.isReadable()) {

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
