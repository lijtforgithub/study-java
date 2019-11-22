package com.ljt.study.pp.ivw;

import java.util.Objects;

/**
 * 编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。
 * 但是要保证汉字不被截半个，如“我ABC”4，应该截为“我AB”，输入“我ABC汉DEF”，6，应该输出为“我ABC”而不是“我ABC+汉的半个”。
 *
 * @author LiJingTang
 * @date 2019-11-21 13:40
 */
public class SplitString {

    public static void main(String[] args) {
        String srcStr1 = "我ABC";
        String srcStr2 = "我ABC汉DEF";

        System.out.println(splitString(srcStr1, 4));
        System.out.println(splitString(srcStr2, 6));
    }

    private static String splitString(String src, int len) {
        if (Objects.isNull(src)) {
            return null;
        }
        byte[] bytes = src.getBytes();
        if (len >= bytes.length) {
            return src;
        }

        // 判断是否出现了截半，截半的话字节对于的ASC码是小于0的值
        return new String(bytes, 0, bytes[len] < 0 ? --len : len);
    }

}
