package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-11-21 13:58
 */
public class ProcessBuilderTest {

    @Test
    public void testRedirectOutput() throws IOException {
        // /c 执行字符串指定的命令然后终止
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "ping www.taobao.com");
        File desktopFile = FileSystemView.getFileSystemView().getHomeDirectory();
        File outputFile = new File(desktopFile, "output.txt");
        pb.redirectOutput(outputFile);
        pb.start();

        System.out.println("子进程执行消息存放在：" + pb.redirectOutput().file().getPath());
    }

    @Test
    public void testDirectory() throws IOException {
        List<String> paramList = new ArrayList<>();
        // 程序要么就是写全路径，要么就是配置系统环境变量才能写简称，如 calc、notepad 等
        paramList.add("C:/windows/system32/notepad.exe"); // 启动系统的记事本，第一个参数必须是可执行程序
        paramList.add("output.txt"); // 记事本打开这个文件
        ProcessBuilder pb = new ProcessBuilder(paramList);
        // 必须注意的是 directory 是为paramList中的参数 "output.txt" 设置的工作目录，而不是程序 "C:/windows/system32/notepad.exe"
        pb.directory(FileSystemView.getFileSystemView().getHomeDirectory()); // 文件所在文件夹
        pb.start();

        System.out.println("当前工作目录：" + pb.directory());// 默认情况下，未设置时，输出为 null
    }

    @Test
    public void testWindowCmd() throws IOException {
        ProcessBuilder pb = new ProcessBuilder();
//        System.out.println(pb.environment());
        pb.redirectErrorStream(true); // 重定向错误输出流到正常输出流

        pb.directory(FileSystemView.getFileSystemView().getHomeDirectory());
        pb.command("cmd", "/c", "dir");
        Process p1 = pb.start();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream(), "gbk"));
        String line1 = null;
        while ((line1 = br1.readLine()) != null) {
            System.out.println(line1);
        }

        System.out.println("----------- 分割线 ----------");

        // 写入到文件 流里没有
        pb.command("cmd", "/c", "dir", ">>", "pb.txt");
        Process p2 = pb.start();

        BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream(), "gbk"));
        String line2 = null;
        while ((line2 = br2.readLine()) != null) {
            System.out.println(line2);
        }
    }

}
