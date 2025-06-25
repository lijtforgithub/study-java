package com.ljt.study.lang.jdk9;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author LiJingTang
 * @date 2025-06-25 10:31
 */
public class TryWithResourceTest {

    @Test
    void tryWithResource() throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File("testRead.txt"));
        PrintWriter writer = new PrintWriter("testWrite.txt");
        try (scanner; writer) {

        }
    }
}
