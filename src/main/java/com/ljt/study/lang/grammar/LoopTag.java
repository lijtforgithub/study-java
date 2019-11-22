package com.ljt.study.lang.grammar;

/**
 * 标签
 *
 * @author LiJingTang
 * @date 2019-11-21 15:02
 */
public class LoopTag {

    public static void main(String[] args) {
        outer : for (int i = 1; i <= 100; i++){
            for (int j = 2; j <= i / 2; j++){
                if (i % j == 0) continue outer; // 结束外层的本次循环
            }

            System.out.println(i);
        }
    }

}
