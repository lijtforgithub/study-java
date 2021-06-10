package com.ljt.study.lang.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Date;

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

    @Test
    public void testBufferStream1() throws IOException {
        FileInputStream fis = new FileInputStream(
                "E:\\Data\\Java SE\\BufferStream.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        int c = 0;
        System.out.println(bis.read());
        System.out.println(bis.read());
        bis.mark(100);
        for (int i = 0; i <= 10 && (c = bis.read()) != -1; i++) {
            System.out.print((char) c + " ");
        }
        System.out.println();
        bis.reset();
        for (int i = 0; i <= 10 && (c = bis.read()) != -1; i++) {
            System.out.print((char) c + " ");
        }
        bis.close();
    }

    @Test
    public void testBufferStream2() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                "E:\\Data\\Java SE\\BufferStream.txt"));
        BufferedReader br = new BufferedReader(new FileReader(
                "E:\\Data\\Java SE\\BufferStream.txt"));
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
        bw.close();
        br.close();
    }

    @Test
    public void testDataStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//在内存中声明了一个字节数组
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeDouble(Math.random());
        dos.writeBoolean(true);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        System.out.println(bais.available());

        DataInputStream dis = new DataInputStream(bais);
        System.out.println(dis.readDouble());//读取顺序和输入顺序一样 先进先出 队列
        System.out.println(dis.readBoolean());

        baos.close();
        dos.close();
        bais.close();
        dis.close();
    }

    @Test
    public void testFileReader() throws IOException {
        File file = new File("E:/StudySpace/IOProject/src/com/ljt/io/TestFileReader.java");
        FileReader reader = new FileReader(file);
        FileWriter writer = new FileWriter("E:/Data/Java SE/FileWriter.java");
        int c;
        while ((c = reader.read()) != -1) {
            System.out.print((char) c);
            writer.write(c);
        }
        writer.write("****** 复制结束 ******");
        reader.close();
        writer.close();
    }

    @Test
    public void testFileStream() throws IOException {
//      File file = new File("E:/StudySpace/IOProject/src/com/ljt/io/TestFileStream.java");
//		File file = new File(System.getProperty("user.dir")+"/src/com/ljt/io/TestFileStream.java");
        File file = new File("src/com/ljt/io/TestFileStream.java");
        FileInputStream fileIn = new FileInputStream(file);
        FileOutputStream fileOut = new FileOutputStream("E:/Data/Java SE/FileOutStream.java");
        int c;
        while ((c = fileIn.read()) != -1) {
            System.out.print((char) c);
            fileOut.write(c);
        }
        fileOut.close();
        fileIn.close();
    }

    @Test
    public void testPrintStream() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("E:/Data/Java SE/PrintStream.txt");
        PrintStream ps = new PrintStream(fos);

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

    @Test
    public void testPrintWriter() throws IOException {
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileWriter fw = new FileWriter("E:\\Data\\Java SE\\logfile.log", true);
        PrintWriter pw = new PrintWriter(fw);
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
        pw.close();
    }

    @Test
    public void testStreamTransform() throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream("E:\\Data\\Java SE\\Transform.txt"));
        osw.write("mircosoft_ibm_sun_apple_hp");
        System.out.println(osw.getEncoding());
        osw.close();
        osw = new OutputStreamWriter(new FileOutputStream(
                "E:\\Data\\Java SE\\Transform.txt", true), "ISO8859_1"); // latin-1
        osw.write("mircosoft_ibm_sun_apple_hp");
        System.out.println(osw.getEncoding());
        osw.close();
    }

}
