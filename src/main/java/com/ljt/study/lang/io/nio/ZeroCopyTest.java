package com.ljt.study.lang.io.nio;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 零拷贝 sendfile
 *
 * @author LiJingTang
 * @date 2021-08-29 22:14
 */
class ZeroCopyTest {

    private static final File DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory();

    @Test
    @SneakyThrows
    void mmap() {
        File f = new File(DESKTOP.getAbsolutePath() + File.separator + "test-mmap.txt");
        try (RandomAccessFile file = new RandomAccessFile(f, "rw")) {
            long size = 2048L;
            // mmap 内存直接映射
            MappedByteBuffer byteBuffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);

            System.out.println(byteBuffer.isReadOnly());
            System.out.println(byteBuffer);

            // 写数据之后，JVM 退出之后会强制刷新的
            byteBuffer.put("a".getBytes());
            byteBuffer.put("b".getBytes());
            byteBuffer.put("c".getBytes());
            byteBuffer.put("d".getBytes());

            System.out.println(byteBuffer);

            // 强制刷盘
            byteBuffer.force();
        }
    }

    @Test
    @SneakyThrows
    void sendfile() {
        File send = new File(DESKTOP.getAbsolutePath() + File.separator + "test-send.txt");
        File recv = new File(DESKTOP.getAbsolutePath() + File.separator + "test-recv.txt");

        // 传统方式写 有用户空间 内核空间拷贝
        try (FileOutputStream output = new FileOutputStream(send)) {
            output.write("Hello 零拷贝".getBytes(StandardCharsets.UTF_8));
            output.flush();
        }

        try (FileInputStream sendFile = new FileInputStream(send);
             FileOutputStream recvFile = new FileOutputStream(recv)) {
            // transferFrom
            sendFile.getChannel().transferTo(0, sendFile.available(), recvFile.getChannel());
        }
    }

}
