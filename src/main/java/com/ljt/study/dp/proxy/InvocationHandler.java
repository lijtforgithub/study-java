package com.ljt.study.dp.proxy;

import java.lang.reflect.Method;

/**
 * @author LiJingTang
 * @date 2019-12-14 15:08
 */
public interface InvocationHandler {

    void invoke(Object obj, Method method);

}
