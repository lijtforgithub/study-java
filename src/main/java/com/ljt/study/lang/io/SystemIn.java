package com.ljt.study.lang.io;

import java.io.Console;
import java.util.Scanner;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:40
 */
public class SystemIn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        System.out.println(username);

        Console console = System.console();

        if (console == null) {
            System.err.println("Console is null => IDE 控制台不行");
            System.exit(-1);
        }

        String password = new String(console.readPassword());
        System.out.println(password);
    }

}
