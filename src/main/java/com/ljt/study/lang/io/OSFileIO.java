package com.ljt.study.lang.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2021-09-02 14:38
 */
class OSFileIO {

    private static final byte[] DATA = "123456789\n".getBytes();
    private static final File FILE = new File("C:\\Users\\Administrator\\Desktop\\OSFileIO.txt");

    /**
     * 不调用flush 默认使用内核的刷盘逻辑
     */

    @Test
    @SneakyThrows
    void io() {
        FILE.createNewFile();
        FileOutputStream out = new FileOutputStream(FILE);
        while (true) {
            out.write(DATA);
        }
    }

    /**
     * JVM 内部默认缓存 8KB  然后进行一次系统调用 所以速度快
     */
    @Test
    @SneakyThrows
    void buffer() {
        FILE.createNewFile();
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(FILE));
        while (true) {
            out.write(DATA);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        mmap();
        readBigFile(FILE, DATA.length * 10000);
    }

    @SneakyThrows
    private static void readBigFile(File file, long pageSize) {
        RandomAccessFile raFile = new RandomAccessFile(file, "r");
        final FileChannel channel = raFile.getChannel();
        final long total = channel.size();
        final long page = total / pageSize;
        System.out.printf("%d / %d = %d %n", total, pageSize, page);

        TimeUnit.SECONDS.sleep(10);

        ExecutorService executors = Executors.newFixedThreadPool(100);

        for (long i = 0; i <= page; i++) {
            long finalI = i;
            executors.submit(() -> {
                try {
                    MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, finalI * pageSize,
                            Math.min((finalI + 1) * pageSize, total));
                    for (int j = 0; j < buffer.limit(); j++) {
                        System.out.print((char) buffer.get(j));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @SneakyThrows
    private static void mmap() {
        FILE.createNewFile();
        RandomAccessFile file = new RandomAccessFile(FILE, "rw");

        file.write("hello RandomAccessFile\n".getBytes());
        file.write("hello MMAP\n".getBytes());
        System.out.println("write");
        System.in.read();

        file.seek(4);
        file.write("OOXX".getBytes());
        System.out.println("seek");
        System.in.read();

        // 只有FileChannel才可以使用 MMAP
        FileChannel fileChannel = file.getChannel();
        // MMAP 堆外和文件映射的   byte/not obj
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        /*
         * 不是系统调用 但是数据会到达内核的pageCache
         * 曾经我们是需要out.write() 这样的系统调用 才能让程序的data进入内核的pageCache（必须有用户态内核态切换）
         * MMAP 的内存映射，依然是内核的pageCache体系所约束的（换言之 丢数据）
         * 可以去github上找一些 其他C程序员写的jni扩展库 使用linux内核的Direct IO（数据库一般使用）
         * 直接IO是忽略linux的pageCache（把pageCache 交给了程序自己开辟一个字节数组当作pageCache 动用代码逻辑来维护一致性/dirty。。。一系列复杂问题）
         */
        map.put("@@@".getBytes());
        System.out.println("MMAP");
        System.in.read();

        // flush
        map.force();

        file.seek(0);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        fileChannel.read(buffer);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);

        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print((char) buffer.get(i));
        }
    }

}
