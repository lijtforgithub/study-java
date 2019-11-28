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
        String str = "hello,world!china!"; // 定义字符串
        ByteArrayInputStream byteInput = new ByteArrayInputStream(str.getBytes()); // 创建字节数组流对象，将字符串str拆分成字节
        PushbackInputStream pbInput = new PushbackInputStream(byteInput); // 创建回退流对象，将拆解的字节数组流传入
        int temp = 0; // 临时变量，存放逐字节读取

        while ((temp = pbInput.read()) != -1) { // push.read()逐字节读取存放在temp中，如果读取完成返回-1
            if (temp == ',' || temp == '!') { // 判断读取的是否是逗号
                pbInput.unread(temp); // 是的话把逗号抛回输入流缓冲区
                temp = pbInput.read(); // 回退到缓冲区前面
                System.out.print(" (回退 " + (char) temp + ") "); // 输出回退的字符
            } else {
                System.out.print((char) temp); // 否则输出字符
            }
        }
    }

}
