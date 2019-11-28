package com.ljt.study.lang.io.nio;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LiJingTang
 * @date 2019-11-28 13:06
 */
public class ChannelTest {

    @Test
    public void testGetChannel() throws Exception {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\规范\\xxx.txt", "r");
        FileChannel channel = file.getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        channel.read(buff);
        System.out.println(new String(buff.array()));
    }

}
