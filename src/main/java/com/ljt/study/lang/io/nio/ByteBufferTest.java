package com.ljt.study.lang.io.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author LiJingTang
 * @date 2019-11-28 13:04
 */
public class ByteBufferTest {

    private static final String NIO = "Hello Java NIO";
    private static final ByteBuffer BYTE_BUFF = ByteBuffer.allocate(20).put(NIO.getBytes());

    @Test
    public void testGet() {
        System.out.println(new String(new byte[8]));
        System.out.println(new String(BYTE_BUFF.array()));
        System.out.println(new String(BYTE_BUFF.array()).trim());

        byte[] bytes = new byte[BYTE_BUFF.position()];
        BYTE_BUFF.flip(); // 写入模式切换到读取模式
        BYTE_BUFF.get(bytes);
        System.out.println(new String(bytes));
    }

    @Test
    public void testMark() {
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
    public void testRewind() {
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
    public void testClear() {
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
    public void testCompact() {
        BYTE_BUFF.flip();
        BYTE_BUFF.get();
        BYTE_BUFF.get();
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
