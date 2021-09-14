package com.ljt.study.lang.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 零拷贝 sendfile
 *
 * @author LiJingTang
 * @date 2021-08-29 22:14
 */
class ZeroCopyTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\mmap.txt", "rw");
        long size = 2048L;
        // mmap
        MappedByteBuffer mmap = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);

        System.out.println(mmap.isReadOnly());
        System.out.println(mmap.position());
        System.out.println(mmap.limit());

        // 写数据之后，JVM 退出之后会强制刷新的
        mmap.put("a".getBytes());
        mmap.put("b".getBytes());
        mmap.put("c".getBytes());
        mmap.put("d".getBytes());

        System.out.println(mmap.position());
        System.out.println(mmap.limit());

        mmap.force();

        // sendfile : file.getChannel().transferTo()
    }

}
