package com.ljt.study.lang.jdk.jdk13;

import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:36
 */
public class FileSystemsTest {

    @Test
    void test() {
        FileSystem fileSystem = FileSystems.getDefault();
        fileSystem.getRootDirectories().forEach(System.out::println);
        System.out.println(fileSystem.getSeparator());
    }

}
