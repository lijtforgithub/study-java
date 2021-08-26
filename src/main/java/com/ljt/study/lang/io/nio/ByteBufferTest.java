package com.ljt.study.lang.io.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author LiJingTang
 * @date 2019-11-28 13:04
 */
class ByteBufferTest {

    private static final String NIO = "Hello Java NIO";
    private static final ByteBuffer BYTE_BUFF = ByteBuffer.allocate(20).put(NIO.getBytes());
    private static final int MAX = 1024;

    @Test
    void allocate() {
        // HeapByteBuffer 堆内存
        ByteBuffer buf = ByteBuffer.allocate(MAX);
        System.out.println(buf);
        ByteBuffer wrapBuf = ByteBuffer.wrap(NIO.getBytes());
        System.out.println(wrapBuf);

        // DirectByteBuffer 堆外内存
        ByteBuffer directBuf = ByteBuffer.allocateDirect(MAX);
        System.out.println(directBuf);
    }

    @Test
    void putInt() {
        ByteBuffer buf = ByteBuffer.allocate(MAX);
        System.out.println(buf);

        buf.putInt(1);
        buf.putInt(2);
        buf.putInt(3);
        System.out.println(buf);
        // 可写位
        System.out.println(buf.remaining());

        buf.flip();
        System.out.println(buf.getInt());
        System.out.println(buf.getInt());
        System.out.println(buf.getInt());

        System.out.println(buf);
        // 可读位
        System.out.println(buf.remaining());
    }

    @Test
    void get() {
        System.out.println(new String(new byte[8]));
        System.out.println(new String(BYTE_BUFF.array()));
        System.out.println(new String(BYTE_BUFF.array()).trim());

        byte[] bytes = new byte[BYTE_BUFF.position()];
        BYTE_BUFF.flip(); // 写入模式切换到读取模式
        BYTE_BUFF.get(bytes);
        System.out.println(new String(bytes));
    }


    @Test
    void mark() {
        int m = 5;
        int len = BYTE_BUFF.position();
        byte[] bytes = new byte[2 * len - m];
        BYTE_BUFF.flip(); // 写入模式切换到读取模式

        for (int i = 0; i < len; i++) {
            bytes[i] = BYTE_BUFF.get();
            if (i + 1 == m) {
                BYTE_BUFF.mark(); // mark用于临时保存 position的值
                System.out.println("mark = " + BYTE_BUFF.position());
            }
        }

        BYTE_BUFF.reset(); // reset设置position=mark
        for (int i = 0; BYTE_BUFF.hasRemaining(); i++) {
            bytes[len + i] = BYTE_BUFF.get();
        }

        System.out.println(new String(bytes));
    }

    @Test
    void rewind() {
        int len = BYTE_BUFF.position();
        BYTE_BUFF.flip();

        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = BYTE_BUFF.get();
        }
        System.out.println(new String(bytes));

        BYTE_BUFF.rewind(); // rewind会重置 position 为 0，通常用于重新从头读写 Buffer。

        byte[] bytes2 = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes2[i] = BYTE_BUFF.get();
        }
        System.out.println(new String(bytes2));
    }

    @Test
    void clear() {
        BYTE_BUFF.clear(); // clear重置 Buffer 的意思，相当于重新实例化了一样。
        int len = BYTE_BUFF.position();
        BYTE_BUFF.flip();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = BYTE_BUFF.get();
        }
        System.out.println(new String(bytes));
    }

    @Test
    void compact() {
        BYTE_BUFF.flip();
        BYTE_BUFF.get();
        BYTE_BUFF.get();
        // 压缩
        BYTE_BUFF.compact();

        int len = BYTE_BUFF.position();
        BYTE_BUFF.flip();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = BYTE_BUFF.get();
        }
        System.out.println(new String(bytes));
    }

}
