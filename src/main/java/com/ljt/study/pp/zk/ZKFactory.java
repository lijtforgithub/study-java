package com.ljt.study.pp.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author LiJingTang
 * @date 2019-11-28 15:12
 */
public class ZKFactory {

    private static final Logger logger = LoggerFactory.getLogger(ZKFactory.class);

    private static final String ZK_SERVER_IP = "192.168.100.201:2181,192.168.100.202:2181,192.168.100.203:2181";
    private static final int ZK_TIMEOUT = 5000;

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static volatile ZooKeeper zk = null;

    private ZKFactory() {
        super();
    }

    public static ZooKeeper getConnection() {
        if (zk == null) {

            synchronized (ZKFactory.class) {
                if (zk == null) {
                    try {
                        zk = new ZooKeeper(ZK_SERVER_IP, ZK_TIMEOUT, new Watcher() {

                            @Override
                            public void process(WatchedEvent event) {
                                if (Event.KeeperState.SyncConnected == event.getState()) {
                                    latch.countDown();
                                }
                            }
                        });

                        latch.await();
                    } catch (IOException | InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }

        return zk;
    }

    public static ZooKeeper newConnection() {
        ZooKeeper zookeeper = null;
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            zookeeper = new ZooKeeper(ZK_SERVER_IP, ZK_TIMEOUT, new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        countDownLatch.countDown();
                    }
                }
            });

            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }

        return zookeeper;
    }

}
