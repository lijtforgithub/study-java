package com.ljt.study.lang.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:46
 */
public class IOTest {

    @Test
    public void testPushbackInputStream() throws IOException {
        String str = "hello,world!china!";
        ByteArrayInputStream byteInput = new ByteArrayInputStream(str.getBytes());
        // 创建回退流对象，将拆解的字节数组流传入
        PushbackInputStream pbInput = new PushbackInputStream(byteInput);
        int temp = 0;
        // push.read()逐字节读取存放在temp中，如果读取完成返回-1
        while ((temp = pbInput.read()) != -1) {
            if (temp == ',' || temp == '!') {
                // 是的话把逗号抛回输入流缓冲区
                pbInput.unread(temp);
                // 回退到缓冲区前面
                temp = pbInput.read();
                // 输出回退的字符
                System.out.print(" (回退 " + (char) temp + ") ");
            } else {
                System.out.print((char) temp);
            }
        }
    }

}
