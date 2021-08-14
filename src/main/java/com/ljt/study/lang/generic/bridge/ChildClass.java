package com.ljt.study.lang.generic.bridge;

/**
 * @author LiJingTang
 * @date 2021-08-14 09:53
 */
class ChildClass extends SuperClass<String> {

    @Override
    String method(String s) {
        return s;
    }
}
