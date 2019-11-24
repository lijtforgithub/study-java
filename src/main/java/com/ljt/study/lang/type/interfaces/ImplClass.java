package com.ljt.study.lang.type.interfaces;

/**
 * @author LiJingTang
 * @date 2019-11-23 10:42
 */
public class ImplClass implements Interf2 {

    public static void main(String[] args) {
        ImplClass impl = new ImplClass();
        System.out.println(impl.STR);
    }
    
}

interface Interf1 {

    String STR = "Hello Java";

}

interface Interf2 extends Interf1 {

    String STR = "Hello World";

}
