package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:24
 */
public class FileTest {

    private static final String PATH = System.getProperty("user.dir") + File.separator;

    @Test
    public void testConstant() {
        System.out.println("与系统有关的路径分隔符：" + File.pathSeparator);
        System.out.println("与系统有关的路径分隔符：" + File.pathSeparatorChar);
        System.out.println("与系统有关的默认名称分隔符：" + File.separator);
        System.out.println("与系统有关的默认名称分隔符：" + File.separatorChar);
    }

    @Test
    public void testMethod() {
        File file = FileSystemView.getFileSystemView().getHomeDirectory();
        if (!file.exists()) {
            file.mkdirs();
        }

        System.out.println("是否为绝对路径名: " + file.isAbsolute());
        System.out.println("是否是一个目录: " + file.isDirectory());
        System.out.println("绝对路径名字符串: " + file.getAbsolutePath());
        System.out.println("目录中的文件和目录: " + Arrays.toString(file.list()));

        File f1 = new File("testFile");

        System.out.println(f1.isAbsolute());
        System.out.println(f1.isDirectory());
        System.out.println(f1.getAbsolutePath());
        System.out.println(f1.getName());
        System.out.println(f1.toURI());
    }

}
