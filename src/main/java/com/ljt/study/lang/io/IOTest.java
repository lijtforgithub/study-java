package com.ljt.study.lang.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:46
 */
class IOTest {

    @Test
    void pushbackInputStream() throws IOException {
        String str = "hello,world!china!";

        try (ByteArrayInputStream byteInput = new ByteArrayInputStream(str.getBytes());
             // 创建回退流对象，将拆解的字节数组流传入
             PushbackInputStream pbInput = new PushbackInputStream(byteInput)) {
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

    @Test
    void bufferStream1() throws IOException {
        try (FileInputStream fis = new FileInputStream("");
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            System.out.println(bis.read());
            System.out.println(bis.read());
            bis.mark(100);
            int c;
            for (int i = 0; i <= 10 && (c = bis.read()) != -1; i++) {
                System.out.print((char) c + " ");
            }
            System.out.println();
            bis.reset();
            for (int i = 0; i <= 10 && (c = bis.read()) != -1; i++) {
                System.out.print((char) c + " ");
            }
        }
    }

    @Test
    void bufferStream2() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(""));
             BufferedReader br = new BufferedReader(new FileReader(""))) {
            String s;
            for (int i = 1; i <= 100; i++) {
                s = String.valueOf(Math.random());
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        }
    }

    @Test
    void dataStream() throws IOException {
        // 在内存中声明了一个字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeDouble(Math.random());
        dos.writeBoolean(true);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        System.out.println(bais.available());

        DataInputStream dis = new DataInputStream(bais);
        // 读取顺序和输入顺序一样 先进先出 队列
        System.out.println(dis.readDouble());
        System.out.println(dis.readBoolean());

        baos.close();
        dos.close();
        bais.close();
        dis.close();
    }

    @Test
    void fileReader() throws IOException {
        try (FileReader reader = new FileReader("");
             FileWriter writer = new FileWriter("")) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
                writer.write(c);
            }
            writer.write("****** 复制结束 ******");
        }
    }

    @Test
    void fileStream() throws IOException {
        try (FileInputStream fileIn = new FileInputStream("");
             FileOutputStream fileOut = new FileOutputStream("")) {
            int c;
            while ((c = fileIn.read()) != -1) {
                System.out.print((char) c);
                fileOut.write(c);
            }
        }
    }

    @Test
    void printStream() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("");
             PrintStream ps = new PrintStream(fos)) {

            System.setOut(ps);

            int count = 0;
            for (char c = 0; c <= 60000; c++) {
                System.out.print(c + " ");
                if (count++ >= 100) {
                    System.out.println();
                    count = 0;
                }
            }
        }
    }

    @Test
    void printWriter() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             FileWriter fw = new FileWriter("", true);
             PrintWriter pw = new PrintWriter(fw)) {
            String s;
            while ((s = br.readLine()) != null) {
                if (s.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println(s.toUpperCase());
                pw.println("-------");
                pw.println(s.toUpperCase());
                pw.flush();
            }
            pw.println("=====" + new Date() + "=====");
            pw.flush();
        }
    }

}
