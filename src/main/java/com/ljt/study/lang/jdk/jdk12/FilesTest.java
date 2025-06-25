package com.ljt.study.lang.jdk.jdk12;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author LiJingTang
 * @date 2025-06-25 14:28
 */
public class FilesTest {

    /**
     * 返回第一个不匹配字符的位置，如果文件相同则返回 -1L
     */
    @Test
    void mismatch() throws IOException {
        Path filePath1 = Files.createTempFile("file1", ".txt");
        System.out.println(filePath1);
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1, "Java 12 Article");
        Files.writeString(filePath2, "Java 12 Article");

        long mismatch = Files.mismatch(filePath1, filePath2);
        System.out.println(-1 == mismatch);
    }

}
